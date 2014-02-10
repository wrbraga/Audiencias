package Utilitarios;

import Agenda.agendaTableModel;
import DB.AfastamentosProcurador;
import DB.HibernateUtil;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import javax.swing.JFileChooser;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;



/**
 *
 * @author wrbraga
 */
public class GeradorPDF {
    private final agendaTableModel agenda;
    private static final Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
    private static final Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static final Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);    
    private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
    
    private static final Font fontCabecalho = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
    private static final Font fontLinha = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
    
    // criação do documento
    private Document document;
    private Image logo;
    
    
    private static enum Colunas {
        DIA(1), HORA(2),  LOCAL(6), PROCESSO(3), CLASSE(4),  PROCURADOR(7), AUDIENCIA(5);
        
        public int valor;
        
        Colunas(int valor) {
            this.valor = valor;
        }
        
    }

    public GeradorPDF(agendaTableModel agenda) {
        this.agenda = agenda;
        document = new Document(PageSize.A4);
        ClassLoader cl = this.getClass().getClassLoader();
        URL url = cl.getResource("Imagens/brasaopreto.jpg");
               
        try {
            logo = Image.getInstance(url);
        } catch(BadElementException | IOException e) {
            
        }
    }
    
    public void abrirDocumento() {
        try {                
            JFileChooser salvandoArquivo = new JFileChooser(); 
            File arq = new File("agenda.pdf");
            salvandoArquivo.setSelectedFile(arq);
            int resultado = salvandoArquivo.showSaveDialog(null);  

            if (resultado == JFileChooser.APPROVE_OPTION) {  
                PdfWriter.getInstance(document, new FileOutputStream(salvandoArquivo.getSelectedFile()));                           
            }  

            document.setPageSize(PageSize.A4);
            document.setMargins(5, 5, 5, 5);
            
            document.open();
                    
        } catch(DocumentException de) {
              System.err.println(de.getMessage());
        } catch(IOException ioe) {
              System.err.println(ioe.getMessage());
        }
        
    }
    
    public void fecharDocumento() {
        document.close();
    }
    
    private void cabecalhoPagina() {
                    // CABECALHO CALENDARIO
            PdfPTable tabelaTitulo = new PdfPTable(3);
            int[] columnWidthsTitulo = {5, 70, 25};            
            
            try {
                tabelaTitulo.setWidthPercentage(100);            
                tabelaTitulo.setWidths(columnWidthsTitulo);

                PdfPCell celula1 = new PdfPCell(logo,true);
                celula1.setBorder(Rectangle.NO_BORDER);
                tabelaTitulo.addCell(celula1);

                PdfPCell celula2 = new PdfPCell(new Phrase("Procuradoria da República no Município de Niterói\nSubcoordenadoria Jurídica",catFont));
                celula2.setBorder(Rectangle.NO_BORDER);
                tabelaTitulo.addCell(celula2);

                PdfPCell celula3 = new PdfPCell(new Phrase(Calendario.nomeMes(Calendario.mes()) + "/" + Calendario.ano(),catFont));
                celula3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                celula3.setBorder(Rectangle.NO_BORDER);
                tabelaTitulo.addCell(celula3);                        

                document.add(tabelaTitulo);
            } catch(Exception e) {
                
            }
    }    
    public void criarListagem() {        
            
        try {                
            cabecalhoPagina();

            PdfPTable table = new PdfPTable(agenda.getColumnCount() - 1);
            table.setSpacingBefore(10);
            table.setWidthPercentage(100);
            int[] columnWidths = {6, 5, 7, 15, 6, 10, 26};
            table.setWidths(columnWidths);
            PdfPCell c1;
                       
            for(Colunas col: Colunas.values()) {
                c1 = new PdfPCell(new Phrase(col.name(), fontCabecalho));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);                            
            }
            
            table.setHeaderRows(1);
            
            for(int row = 0; row < agenda.getRowCount(); row++) {
                for(Colunas col: Colunas.values()) {
                    c1 = new PdfPCell(new Phrase(agenda.getValueAt(row, col.valor).toString(),fontLinha));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);                                                               
                }
            }
            
            document.add(table);            
                        
        } catch(DocumentException de) {
              System.err.println(de.getMessage());
        }
                
    }   // método criarListagem()
    
    public String listaProcurador(int dia) {
        int d;
        String procuradores = "";
        for(int x = 0; x < agenda.getRowCount(); x++) {
            Calendario.setData(agenda.getValueAt(x, Colunas.DIA.valor).toString());
            d = Calendario.dia();
            if(dia == d) {
                procuradores += agenda.getValueAt(x, Colunas.LOCAL.valor).toString() + "-" + agenda.getValueAt(x, Colunas.PROCURADOR.valor).toString();
                if (x < agenda.getRowCount()) {
                    procuradores += "\n";
                }
            }
        }
        return procuradores;
        
    }
    
    public List listaAfastamentos(int mes) {
        String SQL_QUERY_AFASTAMENTOS_MES = "select APP.AFASTAMENTOS.DATAINICIO as DATAINICIO, APP.AFASTAMENTOS.DATAFIM as datafim, APP.AFASTAMENTOS.OBS as obs, APP.PROCURADOR.NOME as procurador , APP.PROCURADOR.SIGLA as sigla from APP.AFASTAMENTOS, APP.PROCURADOR where APP.PROCURADOR.IDPROCURADOR = APP.AFASTAMENTOS.IDPROCURADOR and month(APP.AFASTAMENTOS.DATAINICIO) = :mes and month(APP.AFASTAMENTOS.DATAFIM) = :mes";
        
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createSQLQuery(SQL_QUERY_AFASTAMENTOS_MES);
        query.setParameter("mes", (mes + 1));
                              
        List<Object[]> result = query.list();                
        return result;
    }
    
    public List<AfastamentosProcurador> preencherArrayListAfastadosDia(int dia, List<Object[]> afastados) {

        List<AfastamentosProcurador> listaAfastados = new ArrayList<>();
        
        for(Object[] o: afastados) {
            Object[] aux = o;
            Date di = (Date) aux[0];
            Date df = (Date) aux[1];            

            if (dia >= (Calendario.dia(di)) && dia <= (Calendario.dia(df))) {                
                AfastamentosProcurador a = new AfastamentosProcurador((Date) aux[0],(Date) aux[1],aux[2].toString(),aux[3].toString(),aux[4].toString());
                listaAfastados.add(a);
            }            

        }
        
        return listaAfastados;
                
    }
    
    public void criarCalendario() {
        document.setPageSize(PageSize.A4.rotate());
        document.newPage();
                
        try {
            
            Calendario.setData(agenda.getValueAt(0, Colunas.DIA.valor).toString());
            int inicio = Calendario.diaDaSemana();
            int ultimoDia = Calendario.cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                      
            cabecalhoPagina();
            
            // CONTEUDO DO CALENDARIO
            PdfPTable tabela = new PdfPTable(7);
            tabela.setSpacingBefore(10);
            tabela.setWidthPercentage(100);
            int[] columnWidths = {5, 10, 10, 10, 10, 10, 5};
            tabela.setWidths(columnWidths);
            
            PdfPCell celula;            
            String[] COL = {"DOM", "SEGUNDA", "TERÇA", "QUARTA", "QUINTA", "SEXTA", "SÁB"};
            
            String conteudo;            
            int dia = 1; // Dias no calendário
            int diaAudiencia = 1; // Dias para mostrar os procuradores
            int diaSigla = 1;
            
            List<Object[]> listaDeAfastamentos = listaAfastamentos(Calendario.mes());
            
                        
            for(int linha = 0; linha <= 18; linha++) {                
                
                for(int coluna = 0; coluna < 7; coluna++) {                    
                    float altura = 0f;
                    BaseColor cor = BaseColor.WHITE;
                    int alinhamento = Element.ALIGN_CENTER;
                    
                    conteudo = " ";
                    
                    if (linha == 0) {
                        conteudo = COL[coluna];
                    }
                    
                    if (coluna >= 2 && linha >= 16) {
                        if(linha == 16) {
                            conteudo = "LEGENDA";
                        }
                        PdfPCell celulaLegenda = new PdfPCell(new Phrase(conteudo,fontLinha));                        
                        celulaLegenda.setColspan(5);                        
                        celulaLegenda.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celulaLegenda.setBackgroundColor(BaseColor.GRAY);
                        tabela.addCell(celulaLegenda);
                        
                        break;
                    }
                                                          
                    switch(linha) { 
                        case 1:
                            cor = BaseColor.LIGHT_GRAY;
                            if (coluna >= (inicio - 1)) {
                                conteudo = String.valueOf(dia++);
                            }
                            break;                            
                        case 4:
                        case 7:
                        case 10:
                        case 13:
                        case 16:                           
                            cor = BaseColor.LIGHT_GRAY;
                            if (dia <=  ultimoDia) {
                                conteudo = String.valueOf(dia++);                            
                            }
                    }
                    
                    switch(linha) { 
                        case 2:
                            altura = 50f;
                            if (coluna >= (inicio - 1)) {
                                conteudo = listaProcurador(diaAudiencia++);
                            }
                            break;                                                        
                        case 5:
                        case 8:
                        case 11:
                        case 14:
                        case 17:
                            altura = 50f;
                            if (diaAudiencia <=  ultimoDia) {
                                conteudo = listaProcurador(diaAudiencia++);
                            }
                    }                    
                    
                    List<AfastamentosProcurador> listaAfastados = new ArrayList<>();
                    switch(linha) {
                        case 3:
                            if (coluna >= (inicio - 1)) {
                                listaAfastados = preencherArrayListAfastadosDia(diaSigla, listaDeAfastamentos);                                
                                for(AfastamentosProcurador o: listaAfastados) {
                                    conteudo += o.getSigla() + "  ";
                                }
                                diaSigla++;
                            }
                            break;
                        case 6:
                        case 9:
                        case 12:
                        case 15:
                        case 18:
                            if (diaSigla <=  ultimoDia) {
                                listaAfastados = preencherArrayListAfastadosDia(diaSigla, listaDeAfastamentos);                                                                
                                for(AfastamentosProcurador o: listaAfastados) {
                                    conteudo += o.getSigla() + "  ";
                                }
                                diaSigla++;
                            }
                                                        
                    }
                                        
                    celula = new PdfPCell(new Phrase(conteudo,fontLinha));                                      
                    celula.setFixedHeight(altura);
                    celula.setBackgroundColor(cor);         
                    celula.setHorizontalAlignment(alinhamento);                    
                    
                    tabela.addCell(celula);
                                                           
                }
                              
            }            
            
            document.add(tabela); 
            
        } catch(DocumentException de) {
              System.err.println(de.getMessage());
        }
    }    
      

    
} // fim da classe

package Utilitarios;

import Agenda.agendaTableModel;
import DB.AfastamentosProcurador;
import DB.HibernateUtil;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.hibernate.Query;
import org.hibernate.Session;



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
    private static final Font fontLinhaLegenda = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL);
    
    // criação do documento
    private Document document;
    private Image logo;
    private String nomeArquivo;
    
    
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
            String mes = Calendario.nomeMes(Calendario.mes(agenda.getAgenda(0).getDia()));
            int ano = Calendario.ano(agenda.getAgenda(0).getDia());
            this.nomeArquivo = "Agenda_"+mes+"_"+ano+".pdf";
            File arq = new File(this.nomeArquivo);
            salvandoArquivo.setSelectedFile(arq);
            int resultado = salvandoArquivo.showSaveDialog(null);  

            if (resultado == JFileChooser.APPROVE_OPTION) {  
                PdfWriter.getInstance(document, new FileOutputStream(salvandoArquivo.getSelectedFile()));                           
            }  

            document.setPageSize(PageSize.A4);
            document.setMargins(20, 20, 5, 5);
            
            document.open();
                    
        } catch( DocumentException | IOException de) {
              System.err.println(de.getMessage());
        }
        
    }
    
    public void fecharDocumento() {
        document.close();
        JOptionPane.showMessageDialog(null, "Arquivo " +  this.nomeArquivo +" gerado com sucesso!");
    }
    
    private void cabecalhoPagina() {
            Calendario.setData(agenda.getValueAt(0, Colunas.DIA.valor).toString());
                    // CABECALHO CALENDARIO
            PdfPTable tabelaTitulo = new PdfPTable(3);
            int[] columnWidthsTitulo = {5, 68, 27};            
            
            try {
                tabelaTitulo.setWidthPercentage(100);            
                tabelaTitulo.setWidths(columnWidthsTitulo);

                PdfPCell celula1 = new PdfPCell(logo,true);
                celula1.setBorder(Rectangle.NO_BORDER);
                tabelaTitulo.addCell(celula1);

                PdfPCell celula2 = new PdfPCell(new Phrase("Procuradoria da República no Município de Niterói\nSubcoordenadoria Jurídica",catFont));
                celula2.setBorder(Rectangle.NO_BORDER);
                tabelaTitulo.addCell(celula2);
                
                PdfPCell celula3 = new PdfPCell(); 
                PdfPTable t1 = new PdfPTable(1);
                t1.setHorizontalAlignment(Element.ALIGN_CENTER);
                
                Paragraph p1 = new Paragraph(Calendario.nomeMes(Calendario.mes()) + "/" + Calendario.ano(),catFont);
                p1.setAlignment(Element.ALIGN_CENTER);
                PdfPCell t1c1 = new PdfPCell(p1); 
                t1c1.setBorder(Rectangle.NO_BORDER);
                t1c1.setHorizontalAlignment(Element.ALIGN_RIGHT);                
                
                t1.addCell(t1c1);
                
                Paragraph p2 = new Paragraph(Calendario.getDataAtual() + " " + Calendario.getHoraAtual(),fontLinhaLegenda);
                p2.setAlignment(Element.ALIGN_CENTER);
                PdfPCell t1c2 = new PdfPCell(p2); 
                t1c2.setBorder(Rectangle.NO_BORDER);
                t1c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
                
                t1.addCell(t1c2);
                
                celula3.addElement(t1);
                celula3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                celula3.setBorder(Rectangle.NO_BORDER);
                tabelaTitulo.addCell(celula3);                        

                document.add(tabelaTitulo);
            } catch(DocumentException e) {
                
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
      
    private String listaProcurador(int dia) {
        int d;
        String procuradores = "";
        String p1, p2 = null;
        for(int x = 0; x < agenda.getRowCount(); x++) {
            Calendario.setData(agenda.getValueAt(x, Colunas.DIA.valor).toString());
            d = Calendario.dia();
            if(dia == d) {
                p1 = agenda.getValueAt(x, Colunas.LOCAL.valor).toString() + "-" + agenda.getValueAt(x, Colunas.PROCURADOR.valor).toString();
                if (!p1.equals(p2)) {
                    p2 = p1;
                    if (x < agenda.getRowCount()) {
                        procuradores += p1 + "\n";
                    }
                }                
            }
        }
        return procuradores;
        
    }
    
    private List listaAfastamentos(int mes, int ano) {
        String SQL_QUERY_AFASTAMENTOS_MES = "select A.DATAINICIO as DATAINICIO, A.DATAFIM as datafim, A.OBS as obs, P.NOME as procurador , P.SIGLA as sigla \n" +
            "from APP.AFASTAMENTOS A \n" +
            "inner join APP.PROCURADOR P\n" +
            "on A.IDPROCURADOR = P.IDPROCURADOR\n" +
            "where \n" +
            "month(A.DATAINICIO) = :mes\n" +
            "or \n" +
            "month(A.DATAFIM) = :mes\n" +
            "and year(A.DATAINICIO) = :ano";
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createSQLQuery(SQL_QUERY_AFASTAMENTOS_MES);
        query.setParameter("mes", mes);
        query.setParameter("ano", ano);
                              
        List<Object[]> result = query.list();                
        sessao.close();
        return result;
    }
    
    private List<AfastamentosProcurador> preencherArrayListAfastadosDia(int dia, int mes, List<Object[]> afastados) {

        List<AfastamentosProcurador> listaAfastados = new ArrayList<>();
        
        for(Object[] o: afastados) {
            Object[] aux = o;
            Date di = (Date) aux[0];
            Date df = (Date) aux[1];            
            
            if (dia >= (Calendario.dia(di)) || dia <= (Calendario.dia(df))) {                
                AfastamentosProcurador a = new AfastamentosProcurador((Date) aux[0],(Date) aux[1],aux[2].toString(),aux[3].toString(),aux[4].toString());
                listaAfastados.add(a);
            }            

        }
        
        return listaAfastados;
                
    }
    
    private PdfPTable criarTabelaLegenda(List<Object[]> afastados) {
        String TITULO[] = {"PROCURADOR", "SIGLA","CAUSA DO AFASTAMENTO", "INICIO", "FIM"};
        // CONTEUDO DO CALENDARIO
        PdfPTable tabelaLegenda = new PdfPTable(5);
        tabelaLegenda.setSpacingBefore(0);
        tabelaLegenda.setWidthPercentage(100);
        int[] columnWidths = {10, 5, 10, 5, 5};
        
        try {
            tabelaLegenda.setWidths(columnWidths);
        } catch (DocumentException ex) {
            Logger.getLogger(GeradorPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String conteudo = "";
        PdfPCell celula;
        
        for (String TITULO1 : TITULO) {
            celula = new PdfPCell(new Phrase(TITULO1, fontLinhaLegenda));
            celula.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabelaLegenda.addCell(celula);
        }
              
        tabelaLegenda.setHeaderRows(1);
        
        
        List<AfastamentosProcurador> listaAfastados = new ArrayList<>();
        
        for(Object[] o: afastados) {
            Object[] aux = o;
            AfastamentosProcurador a = new AfastamentosProcurador((Date) aux[0],(Date) aux[1],aux[2].toString(),aux[3].toString(),aux[4].toString());
            listaAfastados.add(a);
        }
        
        
        for (AfastamentosProcurador listaAfastado : listaAfastados) {
            celula = new PdfPCell(new Phrase(listaAfastado.getProcurador(), fontLinhaLegenda));
            celula.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabelaLegenda.addCell(celula);
            celula = new PdfPCell(new Phrase(listaAfastado.getSigla(), fontLinhaLegenda));
            celula.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabelaLegenda.addCell(celula);
            celula = new PdfPCell(new Phrase(listaAfastado.getObs(), fontLinhaLegenda));
            celula.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabelaLegenda.addCell(celula);
            celula = new PdfPCell(new Phrase(Calendario.dateToString(listaAfastado.getDataInicio()), fontLinhaLegenda));
            celula.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabelaLegenda.addCell(celula);
            celula = new PdfPCell(new Phrase(Calendario.dateToString(listaAfastado.getDatafim()), fontLinhaLegenda));
            celula.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabelaLegenda.addCell(celula);
        }
        
        
        return tabelaLegenda;
    }    
    
    public void criarCalendario() {
        document.setPageSize(PageSize.A4.rotate());
        document.newPage();
                
        try {            
            Date dataPrimeiraLinha = Calendario.stringToDate(agenda.getValueAt(0, Colunas.DIA.valor).toString());
            int mesAtual = Calendario.mes(dataPrimeiraLinha)+1;
            int anoAtual = Calendario.ano();
            Calendario.setData("01/" + mesAtual  + "/" + anoAtual);
            int inicio = Calendario.diaDaSemana();            
            int ultimoDia = Calendario.cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            
            
            Calendario.setData(agenda.getValueAt(0, Colunas.DIA.valor).toString());
                                  
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
            
            List<Object[]> listaDeAfastamentos = listaAfastamentos(mesAtual, anoAtual);
            
                        
            for(int linha = 0; linha <= 18; linha++) {                
                
                for(int coluna = 0; coluna < 7; coluna++) {                    
                    float altura = 0f;
                    BaseColor cor = BaseColor.WHITE;
                    int alinhamento = Element.ALIGN_CENTER;
                    
                    conteudo = " ";
                    
                    if (linha == 0) {
                        conteudo = COL[coluna];
                    }
                    
                    // Tabela da legenda
                    if (coluna >= 2 && linha >= 16) {
                        PdfPCell celulaLegenda = new PdfPCell();
                        if(linha == 16) {
                            conteudo = "LEGENDA";
                            celulaLegenda = new PdfPCell(new Phrase(conteudo,fontLinha));
                        }
                        
                        // Tabela com os dados dos afastados
                        if(linha == 17) {                           
                            celulaLegenda = new PdfPCell(criarTabelaLegenda(listaDeAfastamentos));
                            celulaLegenda.setRowspan(2);
                        }                      
                        
                        celulaLegenda.setColspan(5);
                        celulaLegenda.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celulaLegenda.setBackgroundColor(BaseColor.LIGHT_GRAY);
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
                            altura = 60f;
                            if (coluna >= (inicio - 1)) {
                                conteudo = listaProcurador(diaAudiencia++);
                            }
                            break;                                                        
                        case 5:
                        case 8:
                        case 11:
                        case 14:
                        case 17:
                            altura = 60f;
                            if (diaAudiencia <=  ultimoDia) {
                                conteudo = listaProcurador(diaAudiencia++);
                            }                            
                    }                    
                    
                    List<AfastamentosProcurador> listaAfastados;// = new ArrayList<>();
                    switch(linha) {
                        case 3:                            
                            if (coluna >= (inicio - 1)) {
                                listaAfastados = preencherArrayListAfastadosDia(diaSigla,mesAtual, listaDeAfastamentos);                                                                
                                for(AfastamentosProcurador o: listaAfastados) {                                    
                                    Date data = Calendario.stringToDate(diaSigla + "/" + mesAtual + "/" + anoAtual);
                                                                         
                                    if (data.compareTo(o.getDataInicio()) >= 0 && data.compareTo(o.getDatafim()) <= 0) {
                                        conteudo += o.getSigla() + "  ";
                                    }
                                    
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
                                listaAfastados = preencherArrayListAfastadosDia(diaSigla,Calendario.mes(), listaDeAfastamentos);                                                                
                                for(AfastamentosProcurador o: listaAfastados) {
                                    Date data =Calendario.stringToDate(diaSigla + "/" + mesAtual + "/" + anoAtual);
                                                                        
                                    if (data.compareTo(o.getDataInicio()) >= 0 && data.compareTo(o.getDatafim()) <= 0) {
                                        conteudo += o.getSigla() + "  ";
                                    }                                    

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

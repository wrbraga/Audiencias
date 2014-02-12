package Principal;

import Afastamentos.afastamentosTableModel;
import Agenda.agendaTableModel;
import DB.HibernateUtil;
import DB.Local;
import DB.Assunto;
import DB.Classe;
import DB.Procurador;
import DB.Agenda;
import DB.AgendaUtil;
import Local.localTableModel;
import Assunto.assuntoTableModel;
import Classe.classeTableModel;
import DB.Afastamentos;
import Procurador.procuradorTableModel;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import Utilitarios.Calendario;
import Utilitarios.GeradorPDF;

/**
 * @author wesley
 */
public class PrincipalJFrame extends javax.swing.JFrame {
    
    /**
     * QUERYs para manipulação da tabela LOCAL 
     */         
    private static final String SQL_INSERT_LOCAL = "INSERT INTO APP.Local (idLocal, Local) VALUES (:idlocal,:local)";
    private static final String SQL_QUERY_ALL_LOCAL = "FROM Local";
    private static final String SQL_QUERY_ID_LOCAL = "FROM Local WHERE idlocal=%s";
    private static final String SQL_QUERY_LOCAL_LOCAL = "FROM Local WHERE local like %s";
    private static final String SQL_DELETE_LOCAL = "DELETE FROM APP.Local WHERE idlocal=:idlocal";
    private static final String SQL_UPDATE_LOCAL = "UPDATE Local SET local = :local WHERE idlocal = :idlocal";
    
    /**
     * QUERYs para manipulação da tabela ASSUNTO 
     */         
    private static final String SQL_INSERT_ASSUNTO = "INSERT INTO APP.Assunto (idassunto, assunto) VALUES (:idassunto,:assunto)";
    private static final String SQL_QUERY_ALL_ASSUNTO = "FROM Assunto";
    private static final String SQL_QUERY_ID_ASSUNTO = "FROM Assunto WHERE idassunto=:idassunto";
    private static final String SQL_QUERY_ASSUNTO_ASSUNTO = "FROM Assunto WHERE assunto like :assunto";
    private static final String SQL_DELETE_ASSUNTO = "DELETE FROM APP.Assunto WHERE idassunto=:idassunto";
    private static final String SQL_UPDATE_ASSUNTO = "UPDATE Assunto SET assunto = :assunto WHERE idassunto = :idassunto";    

    /**
     * QUERYs para manipulação da tabela CLASSE 
     */         
    private static final String SQL_INSERT_CLASSE = "INSERT INTO APP.Classe (idclasse, classe) VALUES (:idclasse,:classe)";
    private static final String SQL_QUERY_ALL_CLASSE = "FROM Classe";
    private static final String SQL_QUERY_ID_CLASSE = "FROM Classe WHERE idclasse=:idclasse";
    private static final String SQL_QUERY_CLASSE_CLASSE = "FROM Classe WHERE classe = :classe";
    private static final String SQL_DELETE_CLASSE = "DELETE FROM APP.Classe WHERE idclasse=:idclasse";
    private static final String SQL_UPDATE_CLASSE = "UPDATE APP.Classe SET classe = :classe WHERE idclasse = :idclasse";        
    
    /**
     * QUERYs para manipulação da tabela PROCURADOR
     */         
    private static final String SQL_INSERT_PROCURADOR = "INSERT INTO APP.Procurador (idprocurador, nome, sigla, antiguidade, area, ultimo, atuando) VALUES (:idprocurador, :nome, :sigla, :antiguidade, :area, :ultimo, :atuando)";
    private static final String SQL_QUERY_ALL_PROCURADOR = "FROM Procurador";
    private static final String SQL_QUERY_ID_PROCURADOR = "FROM Procurador WHERE idprocurador = :idprocurador";
    private static final String SQL_DELETE_PROCURADOR = "DELETE FROM APP.PROCURADOR WHERE idprocurador = :idprocurador";
    private static final String SQL_UPDATE_PROCURADOR = "UPDATE APP.PROCURADOR SET nome = :nome, sigla = :sigla, antiguidade = :antiguidade, area = :area, ultimo = :ultimo, atuando = :atuando WHERE idprocurador = :idprocurador";
    private static final String SQL_UPDATE_PROCURADOR_NOME = "UPDATE APP.PROCURADOR SET nome = :nome WHERE idprocurador = :idprocurador";
    
    private static final String SQL_QUERY_PROC_SORT_ANT = "select * from APP.PROCURADOR WHERE area = 'Criminal' order by antiguidade desc";
    
    /**
     * QUERYs para manipulação da tabela AGENDA
     */             
    private static final String SQL_DELETE_AGENDA = "DELETE FROM APP.AGENDA WHERE idagenda = :idagenda";
    private static final String SQL_INSERT_AGENDA = "INSERT INTO APP.Agenda (idagenda, dia, hora, processo, idclasse, idprocurador, idassunto, idlocal) VALUES (:idagenda, :dia, :hora, :processo, :idclasse, :idprocurador, :idassunto, :idlocal)";
    private static final String SQL_QUERY_ALL_AGENDA = "FROM Agenda";
    
    /**
     * QUERYs para manipulação da tabela AFASTAMENTOS
     */                 
    private static final String SQL_INSERT_AFASTAMENTOS = "INSERT INTO APP.Afastamentos (idafastamento, datainicio, datafim, obs, idprocurador) VALUES (:idafastamento, :datainicio, :datafim, :obs, :idprocurador)";
    private static final String SQL_QUERY_ALL_AFASTAMENTOS = "FROM Afastamentos";
    
    private final localTableModel modeloLocal;
    private final assuntoTableModel modeloAssunto;
    private final classeTableModel modeloClasse;
    private final procuradorTableModel modeloProcurador;
    private final agendaTableModel modeloAgenda;
    private final afastamentosTableModel modeloAfastamento;
    
    private final AgendaUtil agendaUtil;
    
    //private final localComboBoxModel modeloComboBox;
    
    public PrincipalJFrame() {
        initComponents();
               
        modeloLocal = new localTableModel();
        modeloAssunto = new assuntoTableModel();
        modeloClasse = new classeTableModel();
        modeloProcurador = new procuradorTableModel();
        modeloAgenda = new agendaTableModel();
        modeloAfastamento = new afastamentosTableModel();
        
        agendaUtil = new AgendaUtil();
                        
        //modeloComboBox = new localComboBoxModel(preencherAgendaComboBoxLocais());
        
        localJTable.setModel(modeloLocal);        
        assuntoJTable.setModel(modeloAssunto);
        classeJTable.setModel(modeloClasse);
        procuradorJTable.setModel(modeloProcurador);
        agendaJTable.setModel(modeloAgenda);
        afastamentoJTable.setModel(modeloAfastamento);
        
        //agendaComboBoxLocal.setModel(modeloComboBox);
        
        preencherAgendaComboBoxLocais();
        preencherAgendaComboBoxAssunto();
        preencherAgendaComboBoxClasse();
        preencherComboBoxProcurador();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        agendaJPanel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        agendaJTable = new javax.swing.JTable();
        PanelButoesAgenda = new javax.swing.JPanel();
        agendaButtonConsultar = new javax.swing.JButton();
        agendaButtonIncluir = new javax.swing.JButton();
        agendaButtonExcluir = new javax.swing.JButton();
        agendaButtonAlterar = new javax.swing.JButton();
        agendaButtonLimpar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        PanelEntradaAgenda = new javax.swing.JPanel();
        idLabelConsultar4 = new javax.swing.JLabel();
        agendaTextFieldId = new javax.swing.JTextField();
        localLabelConsultar4 = new javax.swing.JLabel();
        agendaTextFieldProcesso = new javax.swing.JTextField();
        agendaTextFieldDia = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        agendaTextFieldHora = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        agendaComboBoxClasse = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        agendaComboBoxProcurador = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        agendaComboBoxAssunto = new javax.swing.JComboBox();
        agendaComboBoxLocal = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        localJPanel = new javax.swing.JPanel();
        localLabelId = new javax.swing.JLabel();
        localTextFieldId = new javax.swing.JTextField();
        localLabelLocal = new javax.swing.JLabel();
        localTextFieldLocal = new javax.swing.JTextField();
        localButtonConsultar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        localJTable = new javax.swing.JTable();
        localButtonLimpar = new javax.swing.JButton();
        localButtonIncluir = new javax.swing.JButton();
        localButtonExcluir = new javax.swing.JButton();
        localButtonAlterar = new javax.swing.JButton();
        assuntoJPanel = new javax.swing.JPanel();
        assuntoLabelId = new javax.swing.JLabel();
        assuntoTextFieldId = new javax.swing.JTextField();
        assuntoLabelLocal = new javax.swing.JLabel();
        assuntoTextFieldAssunto = new javax.swing.JTextField();
        assuntoButtonConsultar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        assuntoJTable = new javax.swing.JTable();
        assuntoButtonLimpar = new javax.swing.JButton();
        assuntoButtonIncluir = new javax.swing.JButton();
        assuntoButtonExcluir = new javax.swing.JButton();
        assuntoButtonAlterar = new javax.swing.JButton();
        classeJPanel = new javax.swing.JPanel();
        classeLabelid = new javax.swing.JLabel();
        classeTextFieldId = new javax.swing.JTextField();
        classeLabelclasse = new javax.swing.JLabel();
        classeTextFieldClasse = new javax.swing.JTextField();
        classeButtonConsultar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        classeJTable = new javax.swing.JTable();
        classeButtonLimpar = new javax.swing.JButton();
        classeButtonIncluir = new javax.swing.JButton();
        classeButtonExcluir = new javax.swing.JButton();
        classeButtonAlterar = new javax.swing.JButton();
        procuradorJPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        procuradorJTable = new javax.swing.JTable();
        PanelBotoes = new javax.swing.JPanel();
        procuradorButtonConsultar = new javax.swing.JButton();
        procuradorButtonIncluir = new javax.swing.JButton();
        procuradorButtonExcluir = new javax.swing.JButton();
        procuradorButtonAlterar = new javax.swing.JButton();
        procuradorButtonLimpar = new javax.swing.JButton();
        PanelEntrada = new javax.swing.JPanel();
        procuradorLabelId = new javax.swing.JLabel();
        procuradorTextFieldId = new javax.swing.JTextField();
        procuradorLabelProcurador = new javax.swing.JLabel();
        procuradorTextFieldProcurador = new javax.swing.JTextField();
        procuradorLabelSigla = new javax.swing.JLabel();
        procuradorTextFieldSigla = new javax.swing.JTextField();
        procuradorLabelAntiguidade = new javax.swing.JLabel();
        procuradorTextFieldAntiguidade = new javax.swing.JTextField();
        procuradorLabelArea = new javax.swing.JLabel();
        procuradorTextFieldArea = new javax.swing.JTextField();
        procuradorComboBoxAtuando = new javax.swing.JComboBox();
        procuradorLabelAtuando = new javax.swing.JLabel();
        afastamentosJPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        afastamentosTextFieldDataInicio = new javax.swing.JFormattedTextField();
        localLabelConsultar5 = new javax.swing.JLabel();
        afastamentosTextFieldObs = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        afastamentoComboBoxProcurador = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        afastamentosTextFieldDataFim = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        afastamentosButtonConsultar = new javax.swing.JButton();
        afastamentosButtonIncluir = new javax.swing.JButton();
        afastamentosButtonExcluir = new javax.swing.JButton();
        afastamentosButtonAlterar = new javax.swing.JButton();
        afastamentosButtonLimpar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        afastamentoJTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Controle de Audiências");

        agendaJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        agendaJTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                agendaJTableMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(agendaJTable);

        PanelButoesAgenda.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        agendaButtonConsultar.setText("Consultar");
        agendaButtonConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agendaButtonConsultarActionPerformed(evt);
            }
        });

        agendaButtonIncluir.setText("Incluir");
        agendaButtonIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agendaButtonIncluirActionPerformed(evt);
            }
        });

        agendaButtonExcluir.setText("Excluir");
        agendaButtonExcluir.setEnabled(false);
        agendaButtonExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agendaButtonExcluirActionPerformed(evt);
            }
        });

        agendaButtonAlterar.setText("Alterar");
        agendaButtonAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agendaButtonAlterarActionPerformed(evt);
            }
        });

        agendaButtonLimpar.setText("Limpar");
        agendaButtonLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agendaButtonLimparActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 51, 51));
        jButton1.setText("1) Sortear");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 255, 51));
        jButton2.setText("2) Gerar Agenda PDF");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelButoesAgendaLayout = new javax.swing.GroupLayout(PanelButoesAgenda);
        PanelButoesAgenda.setLayout(PanelButoesAgendaLayout);
        PanelButoesAgendaLayout.setHorizontalGroup(
            PanelButoesAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelButoesAgendaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(agendaButtonConsultar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(agendaButtonIncluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(agendaButtonExcluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(agendaButtonAlterar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(agendaButtonLimpar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelButoesAgendaLayout.setVerticalGroup(
            PanelButoesAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelButoesAgendaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelButoesAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelButoesAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(agendaButtonLimpar)
                        .addComponent(agendaButtonIncluir)
                        .addComponent(agendaButtonExcluir)
                        .addComponent(agendaButtonAlterar)
                        .addComponent(jButton1)
                        .addComponent(jButton2))
                    .addComponent(agendaButtonConsultar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelEntradaAgenda.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        idLabelConsultar4.setText("ID");

        agendaTextFieldId.setText("0");

        localLabelConsultar4.setText("Processo");

        agendaTextFieldProcesso.setText("0000878-30.2012.4.02.5102");

        agendaTextFieldDia.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        agendaTextFieldDia.setText("01/01/2014");

        jLabel2.setText("Dia");

        jLabel3.setText("Hora");

        agendaTextFieldHora.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));
        agendaTextFieldHora.setText("10:00");

        jLabel4.setText("Classe");

        jLabel5.setText("Procurador");

        agendaComboBoxProcurador.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setText("Assunto");

        agendaComboBoxLocal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setText("Local");

        javax.swing.GroupLayout PanelEntradaAgendaLayout = new javax.swing.GroupLayout(PanelEntradaAgenda);
        PanelEntradaAgenda.setLayout(PanelEntradaAgendaLayout);
        PanelEntradaAgendaLayout.setHorizontalGroup(
            PanelEntradaAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEntradaAgendaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEntradaAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelEntradaAgendaLayout.createSequentialGroup()
                        .addComponent(idLabelConsultar4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(agendaTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(agendaTextFieldDia, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(agendaTextFieldHora, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(localLabelConsultar4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(agendaTextFieldProcesso, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelEntradaAgendaLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(agendaComboBoxClasse, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(agendaComboBoxAssunto, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(agendaComboBoxLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelEntradaAgendaLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(agendaComboBoxProcurador, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelEntradaAgendaLayout.setVerticalGroup(
            PanelEntradaAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEntradaAgendaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEntradaAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idLabelConsultar4)
                    .addComponent(agendaTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(agendaTextFieldDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(agendaTextFieldHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(localLabelConsultar4)
                    .addComponent(agendaTextFieldProcesso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEntradaAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(agendaComboBoxClasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(agendaComboBoxAssunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(agendaComboBoxLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelEntradaAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(agendaComboBoxProcurador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelEntradaAgendaLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {agendaTextFieldDia, jLabel2});

        PanelEntradaAgendaLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {agendaTextFieldProcesso, localLabelConsultar4});

        PanelEntradaAgendaLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {agendaComboBoxClasse, jLabel4});

        PanelEntradaAgendaLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {agendaComboBoxProcurador, jLabel5});

        PanelEntradaAgendaLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {agendaComboBoxAssunto, jLabel6});

        PanelEntradaAgendaLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {agendaComboBoxLocal, jLabel7});

        //

        javax.swing.GroupLayout agendaJPanelLayout = new javax.swing.GroupLayout(agendaJPanel);
        agendaJPanel.setLayout(agendaJPanelLayout);
        agendaJPanelLayout.setHorizontalGroup(
            agendaJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(agendaJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(agendaJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 942, Short.MAX_VALUE)
                    .addGroup(agendaJPanelLayout.createSequentialGroup()
                        .addComponent(PanelEntradaAgenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(PanelButoesAgenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        agendaJPanelLayout.setVerticalGroup(
            agendaJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(agendaJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelEntradaAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelButoesAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Horário", agendaJPanel);

        localLabelId.setText("ID");

        localLabelLocal.setText("Local");

        localButtonConsultar.setText("Consultar");
        localButtonConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                localButtonConsultarActionPerformed(evt);
            }
        });

        localJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        localJTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                localJTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(localJTable);

        localButtonLimpar.setText("Limpar");
        localButtonLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                localButtonLimparActionPerformed(evt);
            }
        });

        localButtonIncluir.setText("Incluir");
        localButtonIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                localButtonIncluirActionPerformed(evt);
            }
        });

        localButtonExcluir.setText("Excluir");
        localButtonExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                localButtonExcluirActionPerformed(evt);
            }
        });

        localButtonAlterar.setText("Alterar");
        localButtonAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                localButtonAlterarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout localJPanelLayout = new javax.swing.GroupLayout(localJPanel);
        localJPanel.setLayout(localJPanelLayout);
        localJPanelLayout.setHorizontalGroup(
            localJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(localJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(localJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 932, Short.MAX_VALUE)
                    .addGroup(localJPanelLayout.createSequentialGroup()
                        .addGroup(localJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(localJPanelLayout.createSequentialGroup()
                                .addComponent(localButtonConsultar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(localButtonIncluir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(localButtonExcluir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(localButtonAlterar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(localButtonLimpar))
                            .addGroup(localJPanelLayout.createSequentialGroup()
                                .addGroup(localJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(localLabelLocal)
                                    .addComponent(localLabelId, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(localJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(localTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(localTextFieldLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 552, Short.MAX_VALUE)))
                .addContainerGap())
        );
        localJPanelLayout.setVerticalGroup(
            localJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(localJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(localJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(localLabelId)
                    .addComponent(localTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(localJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(localLabelLocal)
                    .addComponent(localTextFieldLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(localJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(localButtonConsultar)
                    .addComponent(localButtonLimpar)
                    .addComponent(localButtonIncluir)
                    .addComponent(localButtonExcluir)
                    .addComponent(localButtonAlterar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                .addContainerGap())
        );

        localJPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {localLabelId, localLabelLocal, localTextFieldId, localTextFieldLocal});

        jTabbedPane1.addTab("Local", localJPanel);

        assuntoLabelId.setText("ID");

        assuntoLabelLocal.setText("Assunto");

        assuntoButtonConsultar.setText("Consultar");
        assuntoButtonConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assuntoButtonConsultarActionPerformed(evt);
            }
        });

        assuntoJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        assuntoJTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                assuntoJTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(assuntoJTable);

        assuntoButtonLimpar.setText("Limpar");
        assuntoButtonLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assuntoButtonLimparActionPerformed(evt);
            }
        });

        assuntoButtonIncluir.setText("Incluir");
        assuntoButtonIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assuntoButtonIncluirActionPerformed(evt);
            }
        });

        assuntoButtonExcluir.setText("Excluir");
        assuntoButtonExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assuntoButtonExcluirActionPerformed(evt);
            }
        });

        assuntoButtonAlterar.setText("Alterar");
        assuntoButtonAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assuntoButtonAlterarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout assuntoJPanelLayout = new javax.swing.GroupLayout(assuntoJPanel);
        assuntoJPanel.setLayout(assuntoJPanelLayout);
        assuntoJPanelLayout.setHorizontalGroup(
            assuntoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assuntoJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(assuntoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 932, Short.MAX_VALUE)
                    .addGroup(assuntoJPanelLayout.createSequentialGroup()
                        .addGroup(assuntoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(assuntoJPanelLayout.createSequentialGroup()
                                .addComponent(assuntoButtonConsultar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(assuntoButtonIncluir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(assuntoButtonExcluir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(assuntoButtonAlterar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(assuntoButtonLimpar))
                            .addGroup(assuntoJPanelLayout.createSequentialGroup()
                                .addGroup(assuntoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(assuntoLabelLocal)
                                    .addComponent(assuntoLabelId, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(assuntoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(assuntoTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(assuntoTextFieldAssunto, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 552, Short.MAX_VALUE)))
                .addContainerGap())
        );
        assuntoJPanelLayout.setVerticalGroup(
            assuntoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assuntoJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(assuntoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(assuntoLabelId)
                    .addComponent(assuntoTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(assuntoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(assuntoLabelLocal)
                    .addComponent(assuntoTextFieldAssunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(assuntoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(assuntoButtonConsultar)
                    .addComponent(assuntoButtonLimpar)
                    .addComponent(assuntoButtonIncluir)
                    .addComponent(assuntoButtonExcluir)
                    .addComponent(assuntoButtonAlterar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                .addContainerGap())
        );

        assuntoJPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {assuntoLabelId, assuntoLabelLocal, assuntoTextFieldAssunto, assuntoTextFieldId});

        jTabbedPane1.addTab("Assunto", assuntoJPanel);

        classeLabelid.setText("ID");

        classeLabelclasse.setText("Classe");

        classeButtonConsultar.setText("Consultar");
        classeButtonConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classeButtonConsultarActionPerformed(evt);
            }
        });

        classeJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        classeJTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                classeJTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(classeJTable);

        classeButtonLimpar.setText("Limpar");
        classeButtonLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classeButtonLimparActionPerformed(evt);
            }
        });

        classeButtonIncluir.setText("Incluir");
        classeButtonIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classeButtonIncluirActionPerformed(evt);
            }
        });

        classeButtonExcluir.setText("Excluir");
        classeButtonExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classeButtonExcluirActionPerformed(evt);
            }
        });

        classeButtonAlterar.setText("Alterar");
        classeButtonAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classeButtonAlterarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout classeJPanelLayout = new javax.swing.GroupLayout(classeJPanel);
        classeJPanel.setLayout(classeJPanelLayout);
        classeJPanelLayout.setHorizontalGroup(
            classeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(classeJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(classeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 932, Short.MAX_VALUE)
                    .addGroup(classeJPanelLayout.createSequentialGroup()
                        .addGroup(classeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(classeJPanelLayout.createSequentialGroup()
                                .addComponent(classeButtonConsultar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(classeButtonIncluir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(classeButtonExcluir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(classeButtonAlterar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(classeButtonLimpar))
                            .addGroup(classeJPanelLayout.createSequentialGroup()
                                .addGroup(classeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(classeLabelclasse)
                                    .addComponent(classeLabelid, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(classeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(classeTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(classeTextFieldClasse, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 552, Short.MAX_VALUE)))
                .addContainerGap())
        );
        classeJPanelLayout.setVerticalGroup(
            classeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(classeJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(classeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(classeLabelid)
                    .addComponent(classeTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(classeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(classeLabelclasse)
                    .addComponent(classeTextFieldClasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(classeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(classeButtonConsultar)
                    .addComponent(classeButtonLimpar)
                    .addComponent(classeButtonIncluir)
                    .addComponent(classeButtonExcluir)
                    .addComponent(classeButtonAlterar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                .addContainerGap())
        );

        classeJPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {classeLabelclasse, classeLabelid, classeTextFieldClasse, classeTextFieldId});

        jTabbedPane1.addTab("Classe", classeJPanel);

        procuradorJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        procuradorJTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                procuradorJTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(procuradorJTable);

        PanelBotoes.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        procuradorButtonConsultar.setText("Consultar");
        procuradorButtonConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                procuradorButtonConsultarActionPerformed(evt);
            }
        });

        procuradorButtonIncluir.setText("Incluir");
        procuradorButtonIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                procuradorButtonIncluirActionPerformed(evt);
            }
        });

        procuradorButtonExcluir.setText("Excluir");
        procuradorButtonExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                procuradorButtonExcluirActionPerformed(evt);
            }
        });

        procuradorButtonAlterar.setText("Alterar");
        procuradorButtonAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                procuradorButtonAlterarActionPerformed(evt);
            }
        });

        procuradorButtonLimpar.setText("Limpar");
        procuradorButtonLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                procuradorButtonLimparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelBotoesLayout = new javax.swing.GroupLayout(PanelBotoes);
        PanelBotoes.setLayout(PanelBotoesLayout);
        PanelBotoesLayout.setHorizontalGroup(
            PanelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(procuradorButtonConsultar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(procuradorButtonIncluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(procuradorButtonExcluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(procuradorButtonAlterar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(procuradorButtonLimpar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelBotoesLayout.setVerticalGroup(
            PanelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(procuradorButtonConsultar)
                    .addComponent(procuradorButtonIncluir)
                    .addComponent(procuradorButtonExcluir)
                    .addComponent(procuradorButtonAlterar)
                    .addComponent(procuradorButtonLimpar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelEntrada.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        procuradorLabelId.setText("ID");

        procuradorLabelProcurador.setText("Nome");

        procuradorLabelSigla.setText("Sigla");

        procuradorLabelAntiguidade.setText("Antiguidade");

        procuradorLabelArea.setText("Área");

        procuradorComboBoxAtuando.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Não", "Sim" }));

        procuradorLabelAtuando.setText("Atuando");

        javax.swing.GroupLayout PanelEntradaLayout = new javax.swing.GroupLayout(PanelEntrada);
        PanelEntrada.setLayout(PanelEntradaLayout);
        PanelEntradaLayout.setHorizontalGroup(
            PanelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEntradaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(procuradorLabelAntiguidade)
                    .addComponent(procuradorLabelArea)
                    .addComponent(procuradorLabelSigla)
                    .addComponent(procuradorLabelProcurador)
                    .addComponent(procuradorLabelId, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelEntradaLayout.createSequentialGroup()
                        .addComponent(procuradorTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(181, 181, 181)
                        .addComponent(procuradorLabelAtuando)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(procuradorComboBoxAtuando, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(procuradorTextFieldSigla, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(procuradorTextFieldArea, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(procuradorTextFieldAntiguidade, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(procuradorTextFieldProcurador, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(487, Short.MAX_VALUE))
        );
        PanelEntradaLayout.setVerticalGroup(
            PanelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEntradaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(procuradorLabelId)
                    .addComponent(procuradorTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(procuradorLabelAtuando)
                    .addComponent(procuradorComboBoxAtuando, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(procuradorTextFieldProcurador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(procuradorLabelProcurador))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(procuradorLabelSigla)
                    .addComponent(procuradorTextFieldSigla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(procuradorLabelAntiguidade)
                    .addComponent(procuradorTextFieldAntiguidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(procuradorLabelArea)
                    .addComponent(procuradorTextFieldArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        PanelEntradaLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {procuradorLabelAntiguidade, procuradorLabelArea, procuradorLabelId, procuradorLabelProcurador, procuradorLabelSigla, procuradorTextFieldAntiguidade, procuradorTextFieldArea, procuradorTextFieldId, procuradorTextFieldProcurador, procuradorTextFieldSigla});

        javax.swing.GroupLayout procuradorJPanelLayout = new javax.swing.GroupLayout(procuradorJPanel);
        procuradorJPanel.setLayout(procuradorJPanelLayout);
        procuradorJPanelLayout.setHorizontalGroup(
            procuradorJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(procuradorJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(procuradorJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 942, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, procuradorJPanelLayout.createSequentialGroup()
                        .addGroup(procuradorJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(PanelEntrada, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PanelBotoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        procuradorJPanelLayout.setVerticalGroup(
            procuradorJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(procuradorJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Procurador", procuradorJPanel);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setText("Inicio");

        afastamentosTextFieldDataInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        afastamentosTextFieldDataInicio.setText("01/01/2014");

        localLabelConsultar5.setText("Motivo");

        jLabel9.setText("Procurador");

        afastamentoComboBoxProcurador.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel10.setText("Fim");

        afastamentosTextFieldDataFim.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        afastamentosTextFieldDataFim.setText("01/01/2014");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(afastamentosTextFieldDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(afastamentosTextFieldDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(afastamentoComboBoxProcurador, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(71, 71, 71)
                .addComponent(localLabelConsultar5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(afastamentosTextFieldObs)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(afastamentoComboBoxProcurador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(localLabelConsultar5)
                    .addComponent(afastamentosTextFieldObs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(afastamentosTextFieldDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(afastamentosTextFieldDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        afastamentosButtonConsultar.setText("Consultar");
        afastamentosButtonConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                afastamentosButtonConsultarActionPerformed(evt);
            }
        });

        afastamentosButtonIncluir.setText("Incluir");
        afastamentosButtonIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                afastamentosButtonIncluirActionPerformed(evt);
            }
        });

        afastamentosButtonExcluir.setText("Excluir");
        afastamentosButtonExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                afastamentosButtonExcluirActionPerformed(evt);
            }
        });

        afastamentosButtonAlterar.setText("Alterar");
        afastamentosButtonAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                afastamentosButtonAlterarActionPerformed(evt);
            }
        });

        afastamentosButtonLimpar.setText("Limpar");
        afastamentosButtonLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                afastamentosButtonLimparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(afastamentosButtonConsultar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(afastamentosButtonIncluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(afastamentosButtonExcluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(afastamentosButtonAlterar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(afastamentosButtonLimpar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(afastamentosButtonConsultar)
                    .addComponent(afastamentosButtonLimpar)
                    .addComponent(afastamentosButtonIncluir)
                    .addComponent(afastamentosButtonExcluir)
                    .addComponent(afastamentosButtonAlterar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        afastamentoJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Título 1", "Título 2", "Título 3"
            }
        ));
        afastamentoJTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                afastamentoJTableMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(afastamentoJTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 908, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout afastamentosJPanelLayout = new javax.swing.GroupLayout(afastamentosJPanel);
        afastamentosJPanel.setLayout(afastamentosJPanelLayout);
        afastamentosJPanelLayout.setHorizontalGroup(
            afastamentosJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(afastamentosJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(afastamentosJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        afastamentosJPanelLayout.setVerticalGroup(
            afastamentosJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(afastamentosJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Afastamentos", afastamentosJPanel);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Controle de Audiências");
        jLabel1.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void localButtonConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_localButtonConsultarActionPerformed
        if(!localTextFieldId.getText().trim().isEmpty()) {
            executeQuery(String.format(SQL_QUERY_ID_LOCAL, localTextFieldId.getText().trim()));
        } else if(!localTextFieldLocal.getText().trim().isEmpty()) {
            executeQuery(String.format(SQL_QUERY_LOCAL_LOCAL,"'" + localTextFieldLocal.getText().trim()+ "%'"));
        } else {
            executeQuery(SQL_QUERY_ALL_LOCAL);
        }
    }//GEN-LAST:event_localButtonConsultarActionPerformed

    private void localButtonLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_localButtonLimparActionPerformed
        limparFormLocal();
    }//GEN-LAST:event_localButtonLimparActionPerformed

    private void localJTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_localJTableMouseClicked
        int selectedRows = localJTable.getSelectedRow();
        int selectedColumn = localJTable.getSelectedColumn();
        localTextFieldId.setText(modeloLocal.getValueAt(selectedRows, 0).toString());
        localTextFieldLocal.setText(modeloLocal.getValueAt(selectedRows, 1).toString());
    }//GEN-LAST:event_localJTableMouseClicked

    private void localButtonIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_localButtonIncluirActionPerformed
        incluirLocal();
    }//GEN-LAST:event_localButtonIncluirActionPerformed

    private void localButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_localButtonExcluirActionPerformed
        excluirLocal();        
    }//GEN-LAST:event_localButtonExcluirActionPerformed

    private void localButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_localButtonAlterarActionPerformed
        alterarLocal();
    }//GEN-LAST:event_localButtonAlterarActionPerformed

    private void assuntoButtonConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assuntoButtonConsultarActionPerformed
        consultarAssunto();        
    }//GEN-LAST:event_assuntoButtonConsultarActionPerformed

    private void assuntoJTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_assuntoJTableMouseClicked
        int selectedRow = assuntoJTable.getSelectedRow();
        int selectedColumn = assuntoJTable.getSelectedColumn();
                
        assuntoTextFieldId.setText(modeloAssunto.getValueAt(selectedRow,0).toString());
        assuntoTextFieldAssunto.setText(modeloAssunto.getValueAt(selectedRow,1).toString());
                        
    }//GEN-LAST:event_assuntoJTableMouseClicked

    private void assuntoButtonLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assuntoButtonLimparActionPerformed
        modeloAssunto.limpar();
        assuntoTextFieldId.setText("");
        assuntoTextFieldAssunto.setText("");
    }//GEN-LAST:event_assuntoButtonLimparActionPerformed

    private void assuntoButtonIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assuntoButtonIncluirActionPerformed
        incluirAssunto();
    }//GEN-LAST:event_assuntoButtonIncluirActionPerformed

    private void assuntoButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assuntoButtonExcluirActionPerformed
        excluirAssunto();        
    }//GEN-LAST:event_assuntoButtonExcluirActionPerformed

    private void assuntoButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assuntoButtonAlterarActionPerformed
        alterarAssunto();        
    }//GEN-LAST:event_assuntoButtonAlterarActionPerformed

    private void classeButtonConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classeButtonConsultarActionPerformed
        consultarClasse();
    }//GEN-LAST:event_classeButtonConsultarActionPerformed

    private void classeJTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_classeJTableMouseClicked
        int selectedRow = classeJTable.getSelectedRow();
        int selectedColumn = classeJTable.getSelectedColumn();
                
        classeTextFieldId.setText(modeloClasse.getValueAt(selectedRow,0).toString());
        classeTextFieldClasse.setText(modeloClasse.getValueAt(selectedRow,1).toString());
    }//GEN-LAST:event_classeJTableMouseClicked

    private void classeButtonLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classeButtonLimparActionPerformed
        modeloClasse.limpar();
        classeTextFieldId.setText("");
        classeTextFieldClasse.setText("");
    }//GEN-LAST:event_classeButtonLimparActionPerformed

    private void classeButtonIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classeButtonIncluirActionPerformed
        incluirClasse();
    }//GEN-LAST:event_classeButtonIncluirActionPerformed

    private void classeButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classeButtonExcluirActionPerformed
        excluirClasse();
    }//GEN-LAST:event_classeButtonExcluirActionPerformed

    private void classeButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classeButtonAlterarActionPerformed
        alterarClasse();
    }//GEN-LAST:event_classeButtonAlterarActionPerformed

    private void agendaButtonConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agendaButtonConsultarActionPerformed
        consultarAgenda();
    }//GEN-LAST:event_agendaButtonConsultarActionPerformed

    private void agendaJTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_agendaJTableMouseClicked
        int selectedRows = agendaJTable.getSelectedRow();
        int selectedColumn = agendaJTable.getSelectedColumn();
        agendaTextFieldId.setText(modeloAgenda.getValueAt(selectedRows, 0).toString());
        agendaTextFieldDia.setText(modeloAgenda.getValueAt(selectedRows, 1).toString());
        agendaTextFieldHora.setText(modeloAgenda.getValueAt(selectedRows, 2).toString());
        agendaTextFieldProcesso.setText(modeloAgenda.getValueAt(selectedRows, 3).toString());
        agendaComboBoxClasse.setSelectedItem(modeloAgenda.getValueAt(selectedRows, 4).toString());
        agendaComboBoxAssunto.setSelectedItem(modeloAgenda.getValueAt(selectedRows, 5).toString());
        agendaComboBoxLocal.setSelectedItem(modeloAgenda.getValueAt(selectedRows, 6).toString());
        agendaComboBoxProcurador.setSelectedItem(modeloAgenda.getValueAt(selectedRows, 7).toString());
                
    }//GEN-LAST:event_agendaJTableMouseClicked

    private void agendaButtonLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agendaButtonLimparActionPerformed
        limparAgenda();
    }//GEN-LAST:event_agendaButtonLimparActionPerformed

    private void agendaButtonIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agendaButtonIncluirActionPerformed
        incluirAgenda();
    }//GEN-LAST:event_agendaButtonIncluirActionPerformed

    private void agendaButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agendaButtonExcluirActionPerformed
        excluirAgenda();
    }//GEN-LAST:event_agendaButtonExcluirActionPerformed

    private void agendaButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agendaButtonAlterarActionPerformed
        alterarAgenda();
    }//GEN-LAST:event_agendaButtonAlterarActionPerformed

    private void procuradorButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_procuradorButtonAlterarActionPerformed
        alterarProcurador();
    }//GEN-LAST:event_procuradorButtonAlterarActionPerformed

    private void procuradorButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_procuradorButtonExcluirActionPerformed
        excluirProcurador();
    }//GEN-LAST:event_procuradorButtonExcluirActionPerformed

    private void procuradorButtonIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_procuradorButtonIncluirActionPerformed
        incluirProcurador();
    }//GEN-LAST:event_procuradorButtonIncluirActionPerformed

    private void procuradorButtonLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_procuradorButtonLimparActionPerformed
        modeloProcurador.limpar();
        limparFormProcurador();
    }//GEN-LAST:event_procuradorButtonLimparActionPerformed

    private void procuradorJTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_procuradorJTableMouseClicked
        int linha = procuradorJTable.getSelectedRow();
        
        procuradorTextFieldId.setText(modeloProcurador.getValueAt(linha, 0).toString());
        procuradorTextFieldProcurador.setText(modeloProcurador.getValueAt(linha, 1).toString());
        procuradorTextFieldSigla.setText(modeloProcurador.getValueAt(linha, 2).toString());        
        procuradorTextFieldArea.setText(modeloProcurador.getValueAt(linha, 3).toString());
        procuradorTextFieldAntiguidade.setText(modeloProcurador.getValueAt(linha, 4).toString());
        procuradorComboBoxAtuando.setSelectedIndex(Integer.parseInt(modeloProcurador.getValueAt(linha, 6).toString()));        
        
    }//GEN-LAST:event_procuradorJTableMouseClicked

    private void procuradorButtonConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_procuradorButtonConsultarActionPerformed
        consultarProcurador();
    }//GEN-LAST:event_procuradorButtonConsultarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int semanaAnterior = 0 ;
        Procurador proximoProcurador = new Procurador();
                       
        for(int selectedRows = 0; selectedRows < agendaJTable.getRowCount(); selectedRows++) {
            Session sessao = HibernateUtil.getSessionFactory().openSession();        
            String SQL_AREA = "FROM Procurador WHERE area like :area and atuando = 1 ORDER BY antiguidade DESC";
            
            String localExtraido = modeloAgenda.getValueAt(selectedRows, 6).toString().replaceAll("[0123456789]","").trim(); 
            int semanaAtual = Calendario.semana(modeloAgenda.getValueAt(selectedRows, 1).toString());
            
            if (selectedRows > 0) {
                semanaAnterior = Calendario.semana(modeloAgenda.getValueAt(selectedRows - 1, 1).toString());
            } else if (selectedRows == 0) {
                semanaAnterior = semanaAtual;
            }
            
            Query query  = sessao.createQuery(SQL_AREA);            
            query.setParameter("area", localExtraido);
                              
            List resultado = query.list();
                        
            for(int index = 0; index <= (resultado.size()-1); index++) {
                Procurador p = ((Procurador)resultado.get(index));                
                int nIndex = 0;
                
                if (index != (resultado.size()-1)) {
                    nIndex = index+1;
                    proximoProcurador = ((Procurador)resultado.get(index + 1));                
                } else {
                    proximoProcurador = ((Procurador)resultado.get(0));                
                }
                                
                if (p.getUltimo() == 1) { // É o procurador da vez?
                    
                    if (semanaAtual == semanaAnterior) {  // É a mesma semana para atuação
                        Agenda agenda = modeloAgenda.getAgenda(selectedRows);
                        agenda.setIdprocurador(p.getIdProcurador());
                        modeloAgenda.setValueAt(agenda, selectedRows,7);

                    } else {
                        p.setUltimo(0);
                        agendaUtil.setUltimoProcurador(p.getIdProcurador(),0);
                        
                        proximoProcurador.setUltimo(1);
                        agendaUtil.setUltimoProcurador(proximoProcurador.getIdProcurador(), 1);
                        
                        int nSelectRows = 0;
                                                
                        if ( selectedRows != agendaJTable.getRowCount()) {
                            nSelectRows = selectedRows + 1;
                        }
                        
                        Agenda agenda = modeloAgenda.getAgenda(selectedRows);
                        agenda.setIdprocurador(proximoProcurador.getIdProcurador());
                        modeloAgenda.setValueAt(agenda, nSelectRows);
                                                
                    }
                    break;
                }                       
                
            }            
            sessao.close();
        }       
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        gerarAgenda();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void afastamentosButtonConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_afastamentosButtonConsultarActionPerformed
        afastamentosConsultar();
    }//GEN-LAST:event_afastamentosButtonConsultarActionPerformed

    private void afastamentosButtonIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_afastamentosButtonIncluirActionPerformed
        afastamentosIncluir();
    }//GEN-LAST:event_afastamentosButtonIncluirActionPerformed

    private void afastamentosButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_afastamentosButtonExcluirActionPerformed
        afastamentosExcluir();
    }//GEN-LAST:event_afastamentosButtonExcluirActionPerformed

    private void afastamentosButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_afastamentosButtonAlterarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_afastamentosButtonAlterarActionPerformed

    private void afastamentosButtonLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_afastamentosButtonLimparActionPerformed
        limparAfastamentos();
    }//GEN-LAST:event_afastamentosButtonLimparActionPerformed

    private void afastamentoJTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_afastamentoJTableMouseClicked
        int selectRow = afastamentoJTable.getSelectedRow();
        afastamentoPreencherFormulario(selectRow);
    }//GEN-LAST:event_afastamentoJTableMouseClicked


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PrincipalJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrincipalJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrincipalJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrincipalJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PrincipalJFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelBotoes;
    private javax.swing.JPanel PanelButoesAgenda;
    private javax.swing.JPanel PanelEntrada;
    private javax.swing.JPanel PanelEntradaAgenda;
    private javax.swing.JComboBox afastamentoComboBoxProcurador;
    private javax.swing.JTable afastamentoJTable;
    private javax.swing.JButton afastamentosButtonAlterar;
    private javax.swing.JButton afastamentosButtonConsultar;
    private javax.swing.JButton afastamentosButtonExcluir;
    private javax.swing.JButton afastamentosButtonIncluir;
    private javax.swing.JButton afastamentosButtonLimpar;
    private javax.swing.JPanel afastamentosJPanel;
    private javax.swing.JFormattedTextField afastamentosTextFieldDataFim;
    private javax.swing.JFormattedTextField afastamentosTextFieldDataInicio;
    private javax.swing.JTextField afastamentosTextFieldObs;
    private javax.swing.JButton agendaButtonAlterar;
    private javax.swing.JButton agendaButtonConsultar;
    private javax.swing.JButton agendaButtonExcluir;
    private javax.swing.JButton agendaButtonIncluir;
    private javax.swing.JButton agendaButtonLimpar;
    private javax.swing.JComboBox agendaComboBoxAssunto;
    private javax.swing.JComboBox agendaComboBoxClasse;
    private javax.swing.JComboBox agendaComboBoxLocal;
    private javax.swing.JComboBox agendaComboBoxProcurador;
    private javax.swing.JPanel agendaJPanel;
    private javax.swing.JTable agendaJTable;
    private javax.swing.JFormattedTextField agendaTextFieldDia;
    private javax.swing.JFormattedTextField agendaTextFieldHora;
    private javax.swing.JTextField agendaTextFieldId;
    private javax.swing.JTextField agendaTextFieldProcesso;
    private javax.swing.JButton assuntoButtonAlterar;
    private javax.swing.JButton assuntoButtonConsultar;
    private javax.swing.JButton assuntoButtonExcluir;
    private javax.swing.JButton assuntoButtonIncluir;
    private javax.swing.JButton assuntoButtonLimpar;
    private javax.swing.JPanel assuntoJPanel;
    private javax.swing.JTable assuntoJTable;
    private javax.swing.JLabel assuntoLabelId;
    private javax.swing.JLabel assuntoLabelLocal;
    private javax.swing.JTextField assuntoTextFieldAssunto;
    private javax.swing.JTextField assuntoTextFieldId;
    private javax.swing.JButton classeButtonAlterar;
    private javax.swing.JButton classeButtonConsultar;
    private javax.swing.JButton classeButtonExcluir;
    private javax.swing.JButton classeButtonIncluir;
    private javax.swing.JButton classeButtonLimpar;
    private javax.swing.JPanel classeJPanel;
    private javax.swing.JTable classeJTable;
    private javax.swing.JLabel classeLabelclasse;
    private javax.swing.JLabel classeLabelid;
    private javax.swing.JTextField classeTextFieldClasse;
    private javax.swing.JTextField classeTextFieldId;
    private javax.swing.JLabel idLabelConsultar4;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton localButtonAlterar;
    private javax.swing.JButton localButtonConsultar;
    private javax.swing.JButton localButtonExcluir;
    private javax.swing.JButton localButtonIncluir;
    private javax.swing.JButton localButtonLimpar;
    private javax.swing.JPanel localJPanel;
    private javax.swing.JTable localJTable;
    private javax.swing.JLabel localLabelConsultar4;
    private javax.swing.JLabel localLabelConsultar5;
    private javax.swing.JLabel localLabelId;
    private javax.swing.JLabel localLabelLocal;
    private javax.swing.JTextField localTextFieldId;
    private javax.swing.JTextField localTextFieldLocal;
    private javax.swing.JButton procuradorButtonAlterar;
    private javax.swing.JButton procuradorButtonConsultar;
    private javax.swing.JButton procuradorButtonExcluir;
    private javax.swing.JButton procuradorButtonIncluir;
    private javax.swing.JButton procuradorButtonLimpar;
    private javax.swing.JComboBox procuradorComboBoxAtuando;
    private javax.swing.JPanel procuradorJPanel;
    private javax.swing.JTable procuradorJTable;
    private javax.swing.JLabel procuradorLabelAntiguidade;
    private javax.swing.JLabel procuradorLabelArea;
    private javax.swing.JLabel procuradorLabelAtuando;
    private javax.swing.JLabel procuradorLabelId;
    private javax.swing.JLabel procuradorLabelProcurador;
    private javax.swing.JLabel procuradorLabelSigla;
    private javax.swing.JTextField procuradorTextFieldAntiguidade;
    private javax.swing.JTextField procuradorTextFieldArea;
    private javax.swing.JTextField procuradorTextFieldId;
    private javax.swing.JTextField procuradorTextFieldProcurador;
    private javax.swing.JTextField procuradorTextFieldSigla;
    // End of variables declaration//GEN-END:variables

    private int getUltimoIdafastamentos() {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createSQLQuery("select MAX(idafastamento) from APP.AFASTAMENTOS");
        
        List resultado = query.list();        
        if (resultado.get(0) == null) {
            return 0;
        }
        return Integer.parseInt(resultado.get(0).toString()) + 1;
    }
    
    private int getUltimoIdAgenda() {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createSQLQuery("select MAX(idagenda) from APP.AGENDA");
        
        List resultado = query.list();        
        if (resultado.get(0) == null) {
            return 0;
        }
        return Integer.parseInt(resultado.get(0).toString()) + 1;
    }

    private int getUltimoIdAssunto() {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createSQLQuery("select MAX(idassunto) from APP.ASSUNTO");
        
        List resultado = query.list();        
        if (resultado.get(0) == null) {
            return 0;
        }
        return Integer.parseInt(resultado.get(0).toString()) + 1;
    }

    private int getUltimoIdClasse() {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createSQLQuery("select MAX(idclasse) from APP.CLASSE");
        
        List resultado = query.list();        
        if (resultado.get(0) == null) {
            return 0;
        }
        return Integer.parseInt(resultado.get(0).toString()) + 1;
    }

    private int getUltimoIdLocal() {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createSQLQuery("select MAX(idlocal) from APP.LOCAL");
        
        List resultado = query.list();        
        if (resultado.get(0) == null) {
            return 0;
        }
        return Integer.parseInt(resultado.get(0).toString()) + 1;
    }

    private int getUltimoIdProcurador() {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createSQLQuery("select MAX(idprocurador) from APP.PROCURADOR");
        
        List resultado = query.list();        
        if (resultado.get(0) == null) {
            return 0;
        }
        return Integer.parseInt(resultado.get(0).toString()) + 1;
    }
    
    private List<String> preencherAgendaComboBoxLocais() {
        agendaComboBoxLocal.removeAllItems();
        List<String> linha;
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createSQLQuery("SELECT local FROM APP.Local");
        linha = query.list();        
        sessao.close();
        for(String d: linha) {
            agendaComboBoxLocal.addItem(d);
        }
        return linha;
    }
    
    private List<String> preencherAgendaComboBoxAssunto() {
        agendaComboBoxAssunto.removeAllItems();
        List<String> linha;
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createSQLQuery("SELECT assunto FROM APP.Assunto");
        linha = query.list();        
        sessao.close();
        for(String d: linha) {
            agendaComboBoxAssunto.addItem(d);
        }
        return linha;
    }

    private List<Integer> preencherAgendaComboBoxClasse() {
        agendaComboBoxClasse.removeAllItems();
        List<Integer> linha;
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createSQLQuery("SELECT classe FROM APP.Classe");
        linha = query.list();        
        sessao.close();
        for(int d: linha) {
            agendaComboBoxClasse.addItem(String.valueOf(d));
        }
        return linha;
    }
    
    private List<String> preencherComboBoxProcurador() {
        agendaComboBoxProcurador.removeAllItems();
        afastamentoComboBoxProcurador.removeAllItems();
        List<String> linha;
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createSQLQuery("SELECT nome FROM APP.Procurador");
        linha = query.list();        
        sessao.close();
        for(String d: linha) {
            agendaComboBoxProcurador.addItem(d);
            afastamentoComboBoxProcurador.addItem(d);
        }
        return linha;
    }    
 
    private void executeUpdate(String sql) {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        sessao.beginTransaction();        
        Query query = sessao.createSQLQuery(sql);        
        if(sql.contains(":idlocal")) {
            query.setParameter("idlocal", Integer.parseInt(localTextFieldId.getText().trim()));
        } else if(sql.contains(":local")) {
            query.setParameter("local", localTextFieldLocal.getText().trim());
        }
        
        try {
            int i = query.executeUpdate();
            sessao.getTransaction().commit();
            sessao.close();
        } catch(Exception e) {            
        } finally {
            localTextFieldId.setText("");
            localTextFieldLocal.setText("");
        }            
    }

    private void executeQuery(String sql) {  
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        sessao.beginTransaction();
        Query query = sessao.createQuery(sql);
        
        try {
            List resultado = query.list();
            mostrarResultadoLocal(resultado);
        } catch(Exception e) {} 
        
        sessao.close();        
    }

    private void mostrarResultadoLocal(List resultado) {
        for(Object o: resultado) {
            Local local = (Local)o;
            modeloLocal.addLocal(local);
        }
    }

    private void incluirLocal() {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        sessao.beginTransaction();        
        Query query = sessao.createSQLQuery(SQL_INSERT_LOCAL);
        
        Local local = new Local();
        local.setIdlocal(getUltimoIdLocal());
        local.setLocal(localTextFieldLocal.getText().trim());
        
        query.setParameter("idlocal", local.getIdlocal());
        query.setParameter("local", local.getLocal());
        
        try {
            int i = query.executeUpdate();            
            sessao.getTransaction().commit();
            
            modeloLocal.addLocal(local);
                        
            /**
             * Adiciona os novos itens no combobox da guia Agenda.
             */
            agendaComboBoxLocal.addItem(local.getLocal());
            
        } catch(HibernateException | NumberFormatException e) {            
        } finally {
            localTextFieldId.setText("");
            localTextFieldLocal.setText("");
        }            
    }

    private void alterarLocal() {   
        Session sessao = HibernateUtil.getSessionFactory().openSession(); 
        sessao.beginTransaction();
        Query query = sessao.createQuery(SQL_UPDATE_LOCAL);
        query.setParameter("local", localTextFieldLocal.getText().trim());
        query.setParameter("idlocal", Integer.parseInt(localTextFieldId.getText()));

        int selectedRow = localJTable.getSelectedRow();

        Local local = modeloLocal.getLocal(selectedRow);
        local.setIdlocal(Integer.parseInt(localTextFieldId.getText()));
        local.setLocal(localTextFieldLocal.getText());
        modeloLocal.setValueAt(local, selectedRow);        

        try {
            int result = query.executeUpdate();        
            sessao.getTransaction().commit();
            sessao.close();
            
            /**
             * Após a alteração dos dados no cadastro de locais, a lista de locais
             * é recarregada para que possa ter os dados corrigidos.
             */
            preencherAgendaComboBoxLocais();
            
        } catch(Exception e) {        
        }
    }

    private void limparFormLocal() {
        modeloLocal.limpar();
        localTextFieldId.setText("");
        localTextFieldLocal.setText("");
    }

    private void excluirLocal() {          
        agendaComboBoxLocal.removeItem(localTextFieldLocal.getText());
        
        executeUpdate(SQL_DELETE_LOCAL);
        int selectedRow = localJTable.getSelectedRow();        
        modeloLocal.removeLocal(selectedRow);                 
    }

    
    private void incluirAssunto() {
        Assunto assunto = new Assunto();
        assunto.setIdassunto(getUltimoIdAssunto());
        assunto.setAssunto(assuntoTextFieldAssunto.getText());
        modeloAssunto.addAssunto(assunto);
        
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createSQLQuery(SQL_INSERT_ASSUNTO);
        sessao.beginTransaction();

        query.setParameter("idassunto", assunto.getIdassunto());
        query.setParameter("assunto", assunto.getAssunto());
        query.executeUpdate();
        sessao.getTransaction().commit();
        sessao.close();
        
        agendaComboBoxAssunto.addItem(assuntoTextFieldAssunto.getText());
    }

    private void excluirAssunto() {
        modeloAssunto.removeAssunto(assuntoJTable.getSelectedRow());
        agendaComboBoxAssunto.removeItem(assuntoTextFieldAssunto.getText());
        
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        sessao.beginTransaction();
        Query query = sessao.createSQLQuery(SQL_DELETE_ASSUNTO);
        query.setParameter("idassunto", assuntoTextFieldId.getText());
        query.executeUpdate();
        sessao.getTransaction().commit();
        sessao.close();
    }
    
    private void alterarAssunto() {
        Assunto assunto = new Assunto();
        assunto.setIdassunto(Integer.parseInt(assuntoTextFieldId.getText()));
        assunto.setAssunto(assuntoTextFieldAssunto.getText());
        modeloAssunto.setValueAt(assunto, assuntoJTable.getSelectedRow());        
        
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        sessao.beginTransaction();
        Query query = sessao.createQuery(SQL_UPDATE_ASSUNTO);        
        query.setParameter("idassunto", Integer.parseInt(assuntoTextFieldId.getText()));
        query.setParameter("assunto", assuntoTextFieldAssunto.getText());
        query.executeUpdate();
        sessao.getTransaction().commit();
        
        preencherAgendaComboBoxAssunto();
    }
    
    private void consultarAssunto() {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        String sql;       
               
        if(!assuntoTextFieldId.getText().isEmpty()) {
            sql = SQL_QUERY_ID_ASSUNTO;
        } else if(!assuntoTextFieldAssunto.getText().isEmpty()) {
            sql = SQL_QUERY_ASSUNTO_ASSUNTO;
        } else {
            sql = SQL_QUERY_ALL_ASSUNTO;
        }
        
        Query query = sessao.createQuery(sql);
        
        if(!assuntoTextFieldId.getText().isEmpty()) {
            query.setParameter("idassunto", Integer.parseInt(assuntoTextFieldId.getText()));
        } else if(!assuntoTextFieldAssunto.getText().isEmpty()) {
            query.setParameter("assunto", assuntoTextFieldAssunto.getText());
        }
                
        List resultado = query.list();
        
        mostrarResultadoAssunto(resultado);
    }
    
    public void mostrarResultadoAssunto(List resultado) {
        for(Object o: resultado) {
            Assunto dados = (Assunto)o;
            modeloAssunto.addAssunto(dados);
        }
    }

    private void incluirClasse() {
        Classe classe = new Classe();
        classe.setIdclasse(getUltimoIdClasse());
        classe.setClasse(Integer.parseInt(classeTextFieldClasse.getText()));
        modeloClasse.addClasse(classe);
        
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createSQLQuery(SQL_INSERT_CLASSE);
        sessao.beginTransaction();
        query.setParameter("idclasse", classe.getIdclasse());
        query.setParameter("classe", classe.getClasse());
        query.executeUpdate();
        sessao.getTransaction().commit();
        sessao.close();
        
        agendaComboBoxClasse.addItem(classeTextFieldClasse.getText());
    }

    private void excluirClasse() {
        modeloClasse.removeClasse(classeJTable.getSelectedRow());
        agendaComboBoxClasse.removeItem(classeTextFieldClasse.getText());
        
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        sessao.beginTransaction();
        Query query = sessao.createSQLQuery(SQL_DELETE_CLASSE);
        query.setParameter("idclasse", Integer.parseInt(classeTextFieldId.getText()));
        query.executeUpdate();
        sessao.getTransaction().commit();
        sessao.close();
    }
    
    private void alterarClasse() {
        Classe classe = new Classe();
        classe.setIdclasse(Integer.parseInt(classeTextFieldId.getText()));
        classe.setClasse(Integer.parseInt(classeTextFieldClasse.getText()));
        modeloClasse.setValueAt(classe, classeJTable.getSelectedRow()); 
        
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createSQLQuery(SQL_UPDATE_CLASSE);
        sessao.beginTransaction();
        query.setParameter("idclasse", Integer.parseInt(classeTextFieldId.getText()));
        query.setParameter("classe", Integer.parseInt(classeTextFieldClasse.getText()));
        query.executeUpdate();
        sessao.getTransaction().commit();
        sessao.close();           
        
        preencherAgendaComboBoxClasse();
    }
    
    private void consultarClasse() {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        String sql;       
               
        if(!classeTextFieldId.getText().isEmpty()) {
            sql = SQL_QUERY_ID_CLASSE;
        } else if(!classeTextFieldClasse.getText().isEmpty()) {
            sql = SQL_QUERY_CLASSE_CLASSE;
        } else {
            sql = SQL_QUERY_ALL_CLASSE;
        }
        
        Query query = sessao.createQuery(sql);
        
        if(!classeTextFieldId.getText().isEmpty()) {
            query.setParameter("idclasse", Integer.parseInt(classeTextFieldId.getText()));
        } else if(!classeTextFieldClasse.getText().isEmpty()) {
            query.setParameter("classe", Integer.parseInt(classeTextFieldClasse.getText()));
        }
                
        List resultado = query.list();
        
        mostrarResultadoClasse(resultado);
    }
    
    public void mostrarResultadoClasse(List resultado) {
        for(Object o: resultado) {
            Classe dados = (Classe)o;
            modeloClasse.addClasse(dados);
        }
    }

    private void consultarProcurador() {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createQuery(SQL_QUERY_ALL_PROCURADOR);
        
        List resultado = query.list();
        mostrarResultadoProcurador(resultado);
        sessao.close();
    }
    
    public void mostrarResultadoProcurador(List resultado) {
        for(Object o: resultado) {
            Procurador dados = (Procurador)o;
            modeloProcurador.addProcurador(dados);
        }
    }

    private void incluirProcurador() {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        sessao.beginTransaction();
        Query query = sessao.createSQLQuery(SQL_INSERT_PROCURADOR);

        Procurador p = new Procurador();
        p.setIdProcurador(getUltimoIdProcurador());
        p.setProcurador(procuradorTextFieldProcurador.getText());
        p.setSigla(procuradorTextFieldSigla.getText());
        p.setAntiguidade(Integer.parseInt(procuradorTextFieldAntiguidade.getText()));
        p.setArea(procuradorTextFieldArea.getText());
        p.setUltimo(0);
        p.setAtuando(procuradorComboBoxAtuando.getSelectedIndex());
        modeloProcurador.addProcurador(p);        
        
        query.setParameter("idprocurador", p.getIdProcurador());
        query.setParameter("nome", p.getNome());
        query.setParameter("sigla", p.getSigla());
        query.setParameter("antiguidade", p.getAntiguidade());
        query.setParameter("area", p.getArea());
        query.setParameter("ultimo", p.getUltimo());
        query.setParameter("atuando", p.getAtuando());
        
        query.executeUpdate();
        sessao.getTransaction().commit();
        sessao.close();
        
        agendaComboBoxProcurador.addItem(p.getProcurador());
    }

    private void alterarProcurador() {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createSQLQuery(SQL_UPDATE_PROCURADOR);
        
        Procurador p = new Procurador();
        p.setIdProcurador(Integer.parseInt(procuradorTextFieldId.getText()));
        p.setProcurador(procuradorTextFieldProcurador.getText());
        p.setSigla(procuradorTextFieldSigla.getText());
        p.setAntiguidade(Integer.parseInt(procuradorTextFieldAntiguidade.getText()));
        p.setArea(procuradorTextFieldArea.getText());
        p.setUltimo(0);
        p.setAtuando(procuradorComboBoxAtuando.getSelectedIndex());        
        modeloProcurador.setValueAt(p, procuradorJTable.getSelectedRow());

        query.setParameter("idprocurador", p.getIdProcurador());
        query.setParameter("nome", p.getNome());
        query.setParameter("sigla", p.getSigla());
        query.setParameter("antiguidade", p.getAntiguidade());
        query.setParameter("area", p.getArea());
        query.setParameter("ultimo", p.getUltimo());
        query.setParameter("atuando", p.getAtuando());
        
        query.executeUpdate();
        sessao.close();
                       
        preencherComboBoxProcurador();
    }

    private void excluirProcurador() {
        agendaComboBoxProcurador.removeItem(procuradorTextFieldProcurador.getText());
        
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createSQLQuery(SQL_DELETE_PROCURADOR);
        query.setParameter("idprocurador", Integer.parseInt(procuradorTextFieldId.getText()));
        query.executeUpdate();
        sessao.close();
        
        modeloProcurador.removeProcurador(procuradorJTable.getSelectedRow());
        limparFormProcurador();
    }
    
    private void limparFormProcurador() {
        procuradorTextFieldId.setText("");
        procuradorTextFieldProcurador.setText("");
        procuradorTextFieldSigla.setText("");
        procuradorTextFieldAntiguidade.setText("");
        procuradorTextFieldArea.setText("");        
    }

    private void incluirAgenda() {
        Date d = Calendario.stringToDate(agendaTextFieldDia.getText());
        Date t = Calendario.stringToTime(agendaTextFieldHora.getText());
            
        Agenda agenda = new Agenda();
        //agenda.setIdagenda(Integer.parseInt(agendaTextFieldId.getText()));
        agenda.setIdagenda(getUltimoIdAgenda());
        agenda.setDia(d);
        agenda.setHora(t);
        agenda.setProcesso(agendaTextFieldProcesso.getText());
        agenda.setIdclasse(agendaUtil.getIDByClasse(Integer.parseInt(agendaComboBoxClasse.getSelectedItem().toString())));
        agenda.setIdprocurador(agendaUtil.getIDByProcurador(agendaComboBoxProcurador.getSelectedItem().toString()));         
        agenda.setIdassunto(agendaUtil.getIDByAssunto(agendaComboBoxAssunto.getSelectedItem().toString()));
        agenda.setIdlocal(agendaUtil.getIDByLocal(agendaComboBoxLocal.getSelectedItem().toString()));
        
        modeloAgenda.addAgenda(agenda);
        
//        Session sessao = HibernateUtil.getSessionFactory().openSession();
//        Query query = sessao.createSQLQuery(SQL_INSERT_AGENDA);
//        sessao.beginTransaction();
//        
//        query.setParameter("idagenda", Integer.parseInt(agendaTextFieldId.getText()));
//        query.setParameter("dia", d);
//        query.setParameter("hora", t);
//        query.setParameter("processo", agendaTextFieldProcesso.getText());
//        query.setParameter("idclasse", agendaUtil.getIDByClasse(Integer.parseInt(agendaComboBoxClasse.getSelectedItem().toString())));
//        query.setParameter("idprocurador", agendaUtil.getIDByProcurador(agendaComboBoxProcurador.getSelectedItem().toString()));
//        query.setParameter("idassunto", agendaUtil.getIDByAssunto(agendaComboBoxAssunto.getSelectedItem().toString()));
//        query.setParameter("idlocal", agendaUtil.getIDByLocal(agendaComboBoxLocal.getSelectedItem().toString()));
//        
//        query.executeUpdate();
//        sessao.getTransaction().commit();
//        sessao.close();
        
    }
    
    private void consultarAgenda() {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createQuery(SQL_QUERY_ALL_AGENDA);
        List resultado = query.list();
        
        for(Object o: resultado) {
            Agenda dados = (Agenda) o;
            modeloAgenda.addAgenda(dados);
        }
    }

    private void excluirAgenda() {       
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createSQLQuery(SQL_DELETE_AGENDA);
        query.setParameter("idagenda", Integer.parseInt(agendaTextFieldId.getText()));
        query.executeUpdate();
        sessao.close();
        
                
    }

    private void limparAgenda() {
        modeloAgenda.limpar();        
        agendaTextFieldId.setText("");
        agendaTextFieldDia.setText("");
        agendaTextFieldHora.setText("");
        agendaTextFieldProcesso.setText("");
        agendaComboBoxClasse.setSelectedIndex(0);
        agendaComboBoxAssunto.setSelectedIndex(0);
        agendaComboBoxLocal.setSelectedIndex(0);
        agendaComboBoxProcurador.setSelectedIndex(0);        
    }

    private void alterarAgenda() {
        int selectRow = agendaJTable.getSelectedRow();
        Date d = Calendario.stringToDate(agendaTextFieldDia.getText());
        Date t = Calendario.stringToTime(agendaTextFieldHora.getText());
        
        Agenda agenda = modeloAgenda.getAgenda(selectRow);
        agenda.setIdagenda(Integer.parseInt(agendaTextFieldId.getText()));
        agenda.setDia(d);
        agenda.setHora(t);
        agenda.setProcesso(agendaTextFieldProcesso.getText());
        agenda.setIdclasse(agendaUtil.getIDByClasse(Integer.parseInt(agendaComboBoxClasse.getSelectedItem().toString())));
        agenda.setIdprocurador(agendaUtil.getIDByProcurador(agendaComboBoxProcurador.getSelectedItem().toString()));         
        agenda.setIdassunto(agendaUtil.getIDByAssunto(agendaComboBoxAssunto.getSelectedItem().toString()));
        agenda.setIdlocal(agendaUtil.getIDByLocal(agendaComboBoxLocal.getSelectedItem().toString()));
        
        modeloAgenda.setValueAt(agenda, selectRow);
    }

    private void gerarAgenda() {
        GeradorPDF pdf = new GeradorPDF(modeloAgenda);
        pdf.abrirDocumento();
        pdf.criarListagem();
        pdf.criarCalendario();
        pdf.fecharDocumento();        
    }

    private void afastamentosIncluir() {
        Afastamentos afastamento = new Afastamentos();
        afastamento.setIdafastamento(getUltimoIdafastamentos());
        afastamento.setDatainicio(Calendario.stringToDate(afastamentosTextFieldDataInicio.getText()));
        afastamento.setDatafim(Calendario.stringToDate(afastamentosTextFieldDataFim.getText()));
        afastamento.setIdprocurador(agendaUtil.getIDByProcurador(afastamentoComboBoxProcurador.getSelectedItem().toString()));
        afastamento.setObs(afastamentosTextFieldObs.getText().trim());
                
        modeloAfastamento.addAfastamento(afastamento);
        
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        sessao.beginTransaction();
        Query query = sessao.createSQLQuery(SQL_INSERT_AFASTAMENTOS);
        query.setParameter("idafastamento", afastamento.getIdafastamento());
        query.setParameter("datainicio", afastamento.getDatainicio());
        query.setParameter("datafim", afastamento.getDatafim());
        query.setParameter("obs", afastamento.getObs());
        query.setParameter("idprocurador", afastamento.getIdprocurador());
        
        query.executeUpdate();
        sessao.getTransaction().commit();
        sessao.close();
        
    }

    private void limparAfastamentos() {
        modeloAfastamento.limpar();
        
    }

    private void afastamentosExcluir() {
        int selectRow = afastamentoJTable.getSelectedRow();
        modeloAfastamento.removerAfastamento(selectRow);
    }

    private void afastamentoPreencherFormulario(int selectRow) {  
        Afastamentos afastamento = modeloAfastamento.getAfastamento(selectRow);        
        afastamentosTextFieldDataInicio.setText(Calendario.dateToString(afastamento.getDatainicio()));
        afastamentosTextFieldDataFim.setText(Calendario.dateToString(afastamento.getDatafim()));
        afastamentoComboBoxProcurador.setSelectedItem(agendaUtil.getProcuradorByID(afastamento.getIdprocurador()));
        afastamentosTextFieldObs.setText(afastamento.getObs());
        
    }

    private void afastamentosConsultar() {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createQuery(SQL_QUERY_ALL_AFASTAMENTOS);
        
        List resultado = query.list();
        
        for(Object o : resultado) {
            Afastamentos afastamento = (Afastamentos) o;
            modeloAfastamento.addAfastamento(afastamento);
        }
    }
    
}
package DB;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class AgendaUtil {
    private static final String SQL_QUERY_ID_LOCAL = "FROM Local WHERE idlocal=:idlocal";
    private static final String SQL_QUERY_LOCAL_LOCAL = "FROM Local WHERE local like :local";
    
    private static final String SQL_QUERY_ID_ASSUNTO = "FROM Assunto WHERE idassunto=:idassunto";
    private static final String SQL_QUERY_ASSUNTO_ASSUNTO = "FROM Assunto WHERE assunto like :assunto";
    
    private static final String SQL_QUERY_ID_PROCURADOR = "FROM Procurador WHERE idprocurador = :idprocurador";
    private static final String SQL_QUERY_PROCURADOR_PROCURADOR = "FROM Procurador WHERE nome like :nome";
    
    private static final String SQL_QUERY_ID_CLASSE = "FROM Classe WHERE idclasse=:idclasse";    
    private static final String SQL_QUERY_CLASSE_CLASSE = "FROM Classe WHERE classe=:classe";    
    
    public String getLocalByID(int id) {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createQuery(SQL_QUERY_ID_LOCAL);
        query.setParameter("idlocal", id);
        
        List resultado = query.list();
        sessao.close();
        
        Local l = null;
        for(Object d: resultado) {
            l = (Local)d;
        }
        return l.getLocal();
    }
    
    public int getIDByLocal(String local) {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createQuery(SQL_QUERY_LOCAL_LOCAL);
        query.setParameter("local", local);
        
        List resultado = query.list();
        sessao.close();
        
        Local l = null;
        for(Object d: resultado) {
            l = (Local)d;
        }
        return l.getIdlocal();
    }

    public String getAssuntoByID(int id) {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createQuery(SQL_QUERY_ID_ASSUNTO);
        query.setParameter("idassunto", id);
        
        List resultado = query.list();
        sessao.close();
        
        Assunto l = null;
        for(Object d: resultado) {
            l = (Assunto)d;
        }
        return l.getAssunto();
    }
    
    public int getIDByAssunto(String assunto) {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createQuery(SQL_QUERY_ASSUNTO_ASSUNTO);
        query.setParameter("assunto", assunto);
        
        List resultado = query.list();
        sessao.close();
        
        Assunto l = null;
        for(Object d: resultado) {
            l = (Assunto)d;
        }
        return l.getIdassunto();
    }    

    public String getProcuradorByID(int id) {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createQuery(SQL_QUERY_ID_PROCURADOR);
        query.setParameter("idprocurador", id);
        
        List resultado = query.list();
        sessao.close();
        
        Procurador l = null;
        for(Object d: resultado) {
            l = (Procurador)d;
        }
        return l.getProcurador();
    }
    
    public int getIDByProcurador(String nome) {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createQuery(SQL_QUERY_PROCURADOR_PROCURADOR);
        query.setParameter("nome", nome);
        
        List resultado = query.list();
        sessao.close();
        
        Procurador l = null;
        for(Object d: resultado) {
            l = (Procurador)d;
        }
        return l.getIdprocurador();
    }        

    public int getClasseByID(int id) {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createQuery(SQL_QUERY_ID_CLASSE);
        query.setParameter("idclasse", id);
        
        List resultado = query.list();
        sessao.close();
        
        Classe l = null;
        for(Object d: resultado) {
            l = (Classe)d;
        }
        return l.getClasse();
    }
    
    public int getIDByClasse(int classe) {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createQuery(SQL_QUERY_CLASSE_CLASSE);
        query.setParameter("classe", classe);
        
        List resultado = query.list();
        sessao.close();
        
        Classe l = null;
        for(Object d: resultado) {
            l = (Classe)d;
        }
        return l.getIdclasse();
    }            
    
    public void setUltimoProcurador(int idprocurador, int ultimo) {
        String SQL = "UPDATE APP.PROCURADOR SET ultimo = :ultimo WHERE idprocurador = :idprocurador";
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        sessao.beginTransaction();
        
        Query query = sessao.createSQLQuery(SQL);
        query.setParameter("ultimo", ultimo);
        query.setParameter("idprocurador", idprocurador);
        query.executeUpdate();
        
        sessao.getTransaction().commit();
        sessao.close();
    }
    
    public void setUltimoProcurador(Procurador procurador) {
        String SQL = "UPDATE APP.PROCURADOR SET ultimo = :ultimo WHERE idprocurador = :idprocurador";
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        sessao.beginTransaction();
        
        Query query = sessao.createSQLQuery(SQL);
        query.setParameter("idprocurador", procurador.getIdProcurador());
        query.setParameter("ultimo", procurador.getUltimo());

        query.executeUpdate();
        sessao.getTransaction().commit();
        sessao.close();
    }
}

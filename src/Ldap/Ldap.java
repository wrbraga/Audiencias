package Ldap;

import java.util.Hashtable;  
import javax.naming.AuthenticationException;  
import javax.naming.Context;  
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;  
import javax.naming.directory.DirContext;  
import javax.naming.directory.InitialDirContext;  
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;


public class Ldap {  

    private static final String baseDN = "ou=Niteroi,ou=Proc_Municipais,o=PRRJ";
    private static final String server = "ldap://10.84.0.4:389";
    
    public static int Login(String usuario, String senha){  

        String userName = retornaDN(usuario) + "," + baseDN;          
        String newPassword = senha.trim();

        System.out.println(userName);
        
        Hashtable authEnv = new Hashtable(11);  

        authEnv.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");  
        authEnv.put(Context.PROVIDER_URL, server);  
        authEnv.put(Context.SECURITY_AUTHENTICATION, "simple");  
        authEnv.put(Context.SECURITY_PRINCIPAL, userName);  
        authEnv.put(Context.SECURITY_CREDENTIALS, newPassword);  

       try  {  
           DirContext authContext = new InitialDirContext(authEnv);  
           //Autenticado!
           return 0;
        } catch (AuthenticationException authEx) {  
            // Erro na autenticação!
            return 1;
        } catch (NamingException namEx) {  
            // Problemas na conexão!
            return 2;
        }  
    }  

    private static String retornaDN(String nome) {
	// Set up the environment for creating the initial context
        Hashtable<String, Object> env = new Hashtable<>(11);
	env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	env.put(Context.PROVIDER_URL, server + "/" + baseDN);

        String retorno = "";
	try {
	    // Create initial context
	    DirContext ctx = new InitialDirContext(env);
            
            SearchControls ctls = new SearchControls();
            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            
            String filter = "(&(cn="+nome+")(mail=*))";
            
            NamingEnumeration e = ctx.search("", filter, ctls);
            while (e.hasMore()) {
                SearchResult entry = (SearchResult) e.next();
                retorno =  entry.getName();
            }

	    ctx.close();

	} catch (NamingException e) {
	}    
        
        return retorno;
    }
    
 
}  
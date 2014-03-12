package Ldap;

import java.util.Hashtable;  
import javax.naming.AuthenticationException;  
import javax.naming.Context;  
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;  
import javax.naming.directory.Attribute;  
import javax.naming.directory.Attributes;  
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;  
import javax.naming.directory.InitialDirContext;  


public class Login {  

    private static final String baseDN = "ou=Niteroi,ou=Proc_Municipais,o=PRRJ";
    private static final String server = "ldap://10.84.0.4:389";
    
    public static void Login(String usuario, String senha){  

        String userName = "cn=" + usuario + "," + baseDN;          
        String newPassword = senha.trim();

        Hashtable authEnv = new Hashtable(11);  

        authEnv.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");  
        authEnv.put(Context.PROVIDER_URL, server);  
        authEnv.put(Context.SECURITY_AUTHENTICATION, "simple");  
        authEnv.put(Context.SECURITY_PRINCIPAL, userName);  
        authEnv.put(Context.SECURITY_CREDENTIALS, newPassword);  

       try  
        {  
           DirContext authContext = new InitialDirContext(authEnv);  
           System.err.println("Autenticado!");  


        }  
        catch (AuthenticationException authEx)  
        {  
        System.out.println("Erro na autenticação! ");  
        authEx.printStackTrace();  
        }  
        catch (NamingException namEx)  
        {  
        System.out.println("Problemas na conexão! ");  
        //namEx.getCause().printStackTrace();  
        }  
    }  

    public static  void search() {
	// Set up the environment for creating the initial context
        Hashtable<String, Object> env = new Hashtable<>(11);
	env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	env.put(Context.PROVIDER_URL, server + "/" + baseDN);

	try {
	    // Create initial context
	    DirContext ctx = new InitialDirContext(env);

	    // Specify the attributes to match
	    // Ask for objects with the surname ("sn") attribute
	    // with the value "Smith"
	    // and the "mail" attribute.
	    Attributes matchAttrs = new BasicAttributes(true); // ignore case
	    matchAttrs.put(new BasicAttribute("cn", "WRBraga"));
	    matchAttrs.put(new BasicAttribute("dn"));

	    // Search for objects that have those matching attributes
	    NamingEnumeration answer = ctx.search("ou=People", matchAttrs);

	    // Print the answer
	   // Search.printSearchEnumeration(answer);

	    // Close the context when we're done
	    ctx.close();
	} catch (NamingException e) {
	}    
    }
}  
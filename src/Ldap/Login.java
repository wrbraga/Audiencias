package Ldap;

import java.util.Hashtable;  
import javax.naming.AuthenticationException;  
import javax.naming.Context;  
import javax.naming.NamingException;  
import javax.naming.directory.Attribute;  
import javax.naming.directory.Attributes;  
import javax.naming.directory.DirContext;  
import javax.naming.directory.InitialDirContext;  


public class Login {  

    public static void Login(String usuario, String senha){  

        String userName = "cn=" + usuario.trim() + ",ou=Informatica,ou=Niteroi,ou=Proc_Municipais,o=PRRJ";  
        String newPassword = senha;  

        Hashtable authEnv = new Hashtable(11);  

        authEnv.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");  
        authEnv.put(Context.PROVIDER_URL, "ldap://10.111.142.6:389");  
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

}  
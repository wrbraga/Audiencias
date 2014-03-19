package Usuarios;

public class Usuario {
    
    public boolean usuarioAutorizado(String usuario) {
        String nomes[] = {"wrbraga", "jsantos", "ssoares", "AngelaSanchez"};
        boolean retorno = false;
        
        for(String n: nomes) {
            retorno = n.equalsIgnoreCase(usuario);
            if (retorno == true) {
                break;
            }
        }
        
        return retorno;
    }
}

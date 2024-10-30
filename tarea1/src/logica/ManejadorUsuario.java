package logica;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ManejadorUsuario {
    private static ManejadorUsuario instancia = null;
    private Map<String, Usuario> usuarios;

    private ManejadorUsuario() {
        usuarios = new HashMap<>();
    }

    public static ManejadorUsuario getInstancia() {
        if (instancia == null) {
            instancia = new ManejadorUsuario();
        }
        return instancia;
    }

    public void agregarUsuario(Usuario usuario) {
        usuarios.put(usuario.getNickname(), usuario);
    }

    public boolean existeUsuarioNick(String nickname) {
        return usuarios.containsKey(nickname);
    }
    
    public boolean existeUsuarioEmail(String email) {
        return usuarios.values().stream()
                .anyMatch(u -> email.equals(u.getEmail()));
    }

    public void eliminarUsuario(String nickname) {
        usuarios.remove(nickname);
    }

    public Map<String, Usuario> getUsuarios() {
        return new HashMap<>(usuarios);
    }

    public Usuario getUsuario(String nickname) {
        return usuarios.get(nickname);
    }

    public Usuario getUsuarioPorMail(String mail) {
        for (Map.Entry<String, Usuario> u : usuarios.entrySet()) {
			Usuario usr = u.getValue();
			if (usr.getEmail().equals(mail)) {
				return usr;
			}
		}
        return null;
    }
    
    public Map<String, Cliente> getClientes() {
        return usuarios.values().stream()
                .filter(u -> u instanceof Cliente)
                .map(u -> (Cliente) u)
                .collect(Collectors.toMap(Cliente::getNickname, c -> c));
    }

    public Map<String, Aerolinea> getAerolineas() {
        return usuarios.values().stream()
                .filter(u -> u instanceof Aerolinea)
                .map(u -> (Aerolinea) u)
                .collect(Collectors.toMap(Aerolinea::getNickname, a -> a));
    }

	public void borrarUsuarios() {
		usuarios.clear();
		
	}
	
	public ReservaVuelo getReservaVuelo(String idReserva) {
		int idR = Integer.parseInt(idReserva);
		
		for (Usuario u : usuarios.values()) {
			if (u instanceof Cliente) {
				Cliente cliente = (Cliente) u;
				if (cliente.getReservasVuelo().containsKey(idR)) {
					return cliente.getReservasVuelo().get(idR);
				}
			}
		}
		return null;
	}
}
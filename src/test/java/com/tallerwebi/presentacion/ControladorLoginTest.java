package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.ContrasenasDistintas;
import com.tallerwebi.dominio.excepcion.NombreDeUsuarioRepetido;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorLoginTest {

	private ControladorLogin controladorLogin;
	private Usuario usuarioMock;
	private DatosLogin datosLoginMock;
	private HttpServletRequest requestMock;
	private HttpSession sessionMock;
	private ServicioLogin servicioLoginMock;
	private DatosRegistroDTO datosRegistroMock;


	@BeforeEach
	public void init(){
		datosRegistroMock = new DatosRegistroDTO("leo@unlam.com", "Leo","123","123");
		datosLoginMock = new DatosLogin("dami@unlam.com", "123");
		usuarioMock = mock(Usuario.class);
		when(usuarioMock.getEmail()).thenReturn("dami@unlam.com");
		requestMock = mock(HttpServletRequest.class);
		sessionMock = mock(HttpSession.class);
		servicioLoginMock = mock(ServicioLogin.class);
		controladorLogin = new ControladorLogin(servicioLoginMock);
	}

	@Test
	public void loginConUsuarioYPasswordInorrectosDeberiaLlevarALoginNuevamente() {
		// preparacion
		when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(null);

		// ejecucion
		ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Usuario o clave incorrecta"));
		verify(sessionMock, times(0)).setAttribute("ROL", "ADMIN");
	}
	
	@Test
	public void loginConUsuarioYPasswordCorrectosDeberiaLLevarAHome()  {
		// preparacion
		Usuario usuarioEncontradoMock = mock(Usuario.class);
		when(usuarioEncontradoMock.getRol()).thenReturn("ADMIN");

		when(requestMock.getSession()).thenReturn(sessionMock);
		when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(usuarioEncontradoMock);
		
		// ejecucions
		ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);
		
		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
		verify(sessionMock, times(1)).setAttribute("ROL", usuarioEncontradoMock.getRol());
	}

	@Test
	public void registrameSiUsuarioNoExisteDeberiaCrearUsuarioYVolverAlLogin() throws UsuarioExistente, ContrasenasDistintas, NombreDeUsuarioRepetido {

		// ejecucion
		ModelAndView modelAndView = controladorLogin.registrarme(datosRegistroMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
		verify(servicioLoginMock, times(1)).registrar(datosRegistroMock);
	}

	@Test
	public void registrarmeSiUsuarioExisteDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente, ContrasenasDistintas, NombreDeUsuarioRepetido {
		// preparacion
		doThrow(UsuarioExistente.class).when(servicioLoginMock).registrar(datosRegistroMock);

		// ejecucion
		ModelAndView modelAndView = controladorLogin.registrarme(datosRegistroMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("Registro"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("El usuario ya existe"));
	}

	@Test
	public void errorEnRegistrarmeDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente, ContrasenasDistintas, NombreDeUsuarioRepetido {
		// preparacion
		doThrow(RuntimeException.class).when(servicioLoginMock).registrar(datosRegistroMock);

		// ejecucion
		ModelAndView modelAndView = controladorLogin.registrarme(datosRegistroMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("Registro"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Error al registrar el nuevo usuario"));
	}
	@Test
	public void errorRegistrarmeSiContrasenasDistintasDeberiaVolverAFormularioYMostrarError() throws ContrasenasDistintas, UsuarioExistente, NombreDeUsuarioRepetido {

		doThrow(ContrasenasDistintas.class).when(servicioLoginMock).registrar(datosRegistroMock);

		ModelAndView modelAndView = controladorLogin.registrarme(datosRegistroMock);

		assertThat(modelAndView.getViewName(), equalToIgnoringCase("Registro"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Las contraseñas no son iguales"));
	}
	@Test
	public void errorRegistrarmeConNombreYaCargadoDeberiaVolverFormularioYMostrarError() throws ContrasenasDistintas, UsuarioExistente, NombreDeUsuarioRepetido {

		doThrow(NombreDeUsuarioRepetido.class).when(servicioLoginMock).registrar(datosRegistroMock);

		ModelAndView modelAndView = controladorLogin.registrarme(datosRegistroMock);

		assertThat(modelAndView.getViewName(), equalToIgnoringCase("Registro"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Nombre de usuario repetido"));
	}

}

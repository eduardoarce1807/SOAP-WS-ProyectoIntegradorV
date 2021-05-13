package upeu.proyecto5.soap.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;

@EnableWs
@Configuration
public class SoapConfiguration extends WsConfigurerAdapter {

	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context){
		MessageDispatcherServlet message = new MessageDispatcherServlet();
		message.setApplicationContext(context);
		return new ServletRegistrationBean<>(message, "/proyecto5/ws/*");
	}
	
	@Bean(name = "personaDemo")
	public Wsdl11Definition wsdl11Definition() {
		SimpleWsdl11Definition simpleWsdl11Definition = new SimpleWsdl11Definition();
		simpleWsdl11Definition.setWsdl(new ClassPathResource("/wsdl/persona.wsdl"));
		return simpleWsdl11Definition;
	}
	@Bean(name = "categoriaDemo")
	public Wsdl11Definition wsdl11Definition1() {
		SimpleWsdl11Definition simpleWsdl11Definition = new SimpleWsdl11Definition();
		simpleWsdl11Definition.setWsdl(new ClassPathResource("/wsdl/categoria.wsdl"));
		return simpleWsdl11Definition;
	}
	@Bean(name = "docenteDemo")
	public Wsdl11Definition wsdl11Definition2() {
		SimpleWsdl11Definition simpleWsdl11Definition = new SimpleWsdl11Definition();
		simpleWsdl11Definition.setWsdl(new ClassPathResource("/wsdl/docente.wsdl"));
		return simpleWsdl11Definition;
	}
	@Bean(name = "tipoUnidadAcademicaDemo")
	public Wsdl11Definition wsdl11Definition3() {
		SimpleWsdl11Definition simpleWsdl11Definition = new SimpleWsdl11Definition();
		simpleWsdl11Definition.setWsdl(new ClassPathResource("/wsdl/tipoUnidadAcademica.wsdl"));
		return simpleWsdl11Definition;
	}
	@Bean(name = "unidadAcademicaDemo")
	public Wsdl11Definition wsdl11Definition4() {
		SimpleWsdl11Definition simpleWsdl11Definition = new SimpleWsdl11Definition();
		simpleWsdl11Definition.setWsdl(new ClassPathResource("/wsdl/unidadAcademica.wsdl"));
		return simpleWsdl11Definition;
	}
	
}

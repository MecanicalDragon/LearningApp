package net.medrag.something;

import java.io.*;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.servlet.http.*;

/**
 * {@author} Stanislav Tretyakov
 * This is just an example of servlet, that can extract smartcard data
 * <p>
 * 04.07.2019
 */
public class CertServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String cn = getSSLClientCN(request);
        out.println("Hello, " + cn);
    }

    private String getSSLClientCN(HttpServletRequest request) {
        String anon = "Anon";
        System.out.println("===============================");
        System.out.println("New request has been performed.");
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String element = attributeNames.nextElement();
            System.out.println("Element: " + element);
            System.out.println("Value: " + request.getAttribute(element));
        }
        X509Certificate certs[] = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
        String result = anon;
        if (certs == null || certs.length == 0) {
            System.out.println("No x509 recognized.");
            return anon;
        }
        String dn = certs[0].getSubjectX500Principal().getName("RFC1779");
        System.out.println("Extracted certificate:");
        System.out.println(dn);
        try {
            LdapName ldapDN = new LdapName(dn);
            for (Rdn rdn : ldapDN.getRdns()) {
                System.out.print(rdn.getType());
                System.out.print(" = ");
                System.out.println(rdn.getValue());
                if (rdn.getType().equals("CN") && result.equals(anon)) {
                    result = rdn.getValue().toString();
                }
            }
        } catch (InvalidNameException e) {
            return anon;
        }
        return result;
    }
}

/**
 * //   just example of Kotlin code to handle servlet
 * @SpringBootApplication open class WarhammerApp : SpringBootServletInitializer() {
 * @Autowired val servlet: CertServlet? = null
 * @Bean internal open fun myServletRegistration(): ServletRegistrationBean<HttpServlet?> {
 * return ServletRegistrationBean(servlet, "/*")
 * }
 * <p>
 * companion object {
 * @JvmStatic fun main(args: Array<String>) {
 * SpringApplication.run(WarhammerApp::class.java, *args)
 * }
 * }
 * }
 **/

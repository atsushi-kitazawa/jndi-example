package com.example;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.AuthenticationNotSupportedException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 * Hello world!
 *
 */
public class App {

    private static final String LDAP_SERVER_URL = "ldap://localhost:389";

    public static void main(String[] args) {
        try {
            Hashtable<String, String> env = init();
            DirContext ctx = new InitialDirContext(env);
            System.out.println("connected");
            System.out.println(ctx.getEnvironment());

            // search
            // Object o = ctx.lookup("cn=admin");
            // System.out.println(o);

            // bind
            // ctx.bind("cn=hoge", new Hoge("a"));

            ctx.close();

        } catch (AuthenticationNotSupportedException ex) {
            System.out.println("The authentication is not supported by the server");
            ex.printStackTrace();
        } catch (AuthenticationException ex) {
            System.out.println("incorrect password or username");
            ex.printStackTrace();
        } catch (NamingException ex) {
            System.out.println("error when trying to create the context");
            ex.printStackTrace();
        }
    }

    private static Hashtable<String, String> init() {
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, LDAP_SERVER_URL);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "cn=admin,dc=example,dc=org");
        env.put(Context.SECURITY_CREDENTIALS, "admin");
        return env;
    }
}

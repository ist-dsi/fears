package eu.ist.fears.server.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


import jvstm.TransactionalCommand;
import pt.ist.fenixframework.pstm.Transaction;


public class TxFilter implements Filter {

    public void init(FilterConfig config) {
    }

    public void destroy() {
    }

    public void doFilter(final ServletRequest request, 
                         final ServletResponse response, 
                         final FilterChain chain)
        throws IOException, ServletException {

        Transaction.withTransaction(new TransactionalCommand() {
                public void doIt() {
                    try {
                        chain.doFilter(request, response);
                    } catch (Exception exc) {
                        throw new RuntimeException("Error processing a request", exc);
                    }
                }
            });
    }
}

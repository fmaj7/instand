package conf;

import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;

/**
 * Enables cross-origin access (a request from mobile to this service).
 */
public class CorsFilter implements Filter {

    @Override
    public Result filter(FilterChain filterChain, Context context) {
        /**
         * Copied from the web. Seems to be working but needs to be fine-tuned.
         */
        Result result = filterChain.next(context);
        result.addHeader("Access-Control-Allow-Origin", "*");
        result.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        result.addHeader("Access-Control-Max-Age", "0");
        result.addHeader("Access-Control-Allow-Headers", "Content-type, X-Foo-for-demo-only");
        return result;
    }
}

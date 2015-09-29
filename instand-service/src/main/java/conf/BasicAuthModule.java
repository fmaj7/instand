package conf;

import com.google.common.base.Strings;
import com.google.inject.AbstractModule;
import ninja.UsernamePasswordValidator;

public class BasicAuthModule extends AbstractModule {

    private static final String USERNAME = "wdr";
    private static final String PASSWORD = "WDR";

    @Override
    protected void configure() {
        bind(UsernamePasswordValidator.class).toInstance(new UsernamePasswordValidator() {
            @Override
            public boolean validateCredentials(String username, String password) {
                return !(Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password))
                        && username.equals(USERNAME) && password.equals(PASSWORD);
            }
        });
    }
}
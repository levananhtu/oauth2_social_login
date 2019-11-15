package lvat.oauth2sociallogin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private Auth auth = new Auth();
    private OAuth2 oAuth2 = new OAuth2();

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public OAuth2 getOAuth2() {
        return oAuth2;
    }

    public void setOAuth2(OAuth2 oAuth2) {
        this.oAuth2 = oAuth2;
    }

    public static class Auth {
        private String tokenSecret;
        private long tokenExpirationMsec;

        public Auth() {
        }

        public Auth(String tokenSecret, long tokenExpirationMsec) {
            this.tokenSecret = tokenSecret;
            this.tokenExpirationMsec = tokenExpirationMsec;
        }

        public String getTokenSecret() {
            return tokenSecret;
        }

        public void setTokenSecret(String tokenSecret) {
            this.tokenSecret = tokenSecret;
        }

        public long getTokenExpirationMsec() {
            return tokenExpirationMsec;
        }

        public void setTokenExpirationMsec(long tokenExpirationMsec) {
            this.tokenExpirationMsec = tokenExpirationMsec;
        }
    }

    public static final class OAuth2 {
        private List<String> authorizedRedirectUris;

        public OAuth2() {
        }

        public OAuth2(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
        }

        public List<String> getAuthorizedRedirectUris() {
            return authorizedRedirectUris;
        }

        public void setAuthorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
        }
    }
}

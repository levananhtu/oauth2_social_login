package lvat.oauth2sociallogin.model;

public enum AuthProvider {
    LOCAL {
        @Override
        public String toString() {
            return "LOCAL";
        }
    },
    FACEBOOK {
        @Override
        public String toString() {
            return "FACEBOOK";
        }
    },
    GOOGLE {
        @Override
        public String toString() {
            return "GOOGLE";
        }
    },
    GITHUB {
        @Override
        public String toString() {
            return "GITHUB";
        }
    }
}

package models;

public class ZipCode {
    private String code;
    private Location location;

    public ZipCode(String code, Location location) {
        this.code = code;
        this.location = location;
    }

    public static boolean isValid(String code) {
        if (code.length() != 6){
            return false;
        }

        code = code.toUpperCase();

        for(int i = 0; i < 4; i++){
            int currentChar = code.charAt(i);

            if (currentChar < 48 || currentChar > 57) {
                return false;
            }
        }

        for(int i = 4; i < 6; i++){
            int currentChar = code.charAt(i);

            if (currentChar < 65 || currentChar > 90){
                return false;
            }
        }

        return true;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ZipCode zipCode){
            return zipCode.getCode().equals(code);
        }
        if (o instanceof String code){
            return code.equals(this.code);
        }

        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((location == null) ? 0 : location.hashCode());

        return result;
    }
}

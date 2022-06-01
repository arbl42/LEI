import java.io.Serializable;

public class Login implements Serializable{
    private static final long serialVersionUID = -8433373483994332363L;
    private String email;
    private String password;
    

    public Login(Login l){
        this.email = l.getEmail();
        this.password = l.getPassword();
    }

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Login(){
        this.email = "not defined";
        this.password = "not defined";
    }

    // GETS ------------------------------------------------------------------------------------------

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    // SETS ------------------------------------------------------------------------------------------

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Email: ");
        sb.append(email);
        sb.append("; Password: ");
        sb.append(password);
        return sb.toString();
    }


    public Login clone(){
        return new Login(this);
    }

    public boolean equals (Object o){
        if (this == o) return true;
        if( o == null || o.getClass() != this.getClass()) return false;
        Login l = (Login) o;
        return (this.getEmail().equals(l.getEmail()) &&
                this.getPassword().equals(l.getPassword())); 
    }
}

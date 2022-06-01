import java.io.Serializable;


public class Localizacao implements Serializable{
    private static final long serialVersionUID = 3016807162855178942L;

    private double latitude;
    private double longitude;


    public Localizacao() {
        this.latitude = 0;
        this.longitude = 0;
    }

    public Localizacao(Localizacao l) {
        this.latitude = l.getLatitude();
        this.longitude = l.getLongitude();
    }

    public Localizacao(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public Localizacao clone() {
        return new Localizacao(this);
    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Latitude: ");
        s.append(latitude);
        s.append("\nLongitude: ");
        s.append(longitude);
        return s.toString();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || this.getClass() != o.getClass())
            return false;
        Localizacao l = (Localizacao) o;

        return latitude == l.getLatitude() &&
                longitude == l.getLongitude();
    }


    //Metodos de acesso
    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    //Metodos de alteracao
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


//----------------------------------------- Funcoes auxiliares e/ou de teste -------------------------------------------

    public static int getRandomNumber(int min, int max){
        return (int)(Math.random()*((max-min)+1))+min;
    }


    //------- Funcao que calcula a distancia em quilometros entre duas localizacoes --------

    public double distancia (Localizacao l){ 
        double x1 = this.latitude;
        double x2 = l.getLatitude();
        double y1 = this.longitude;
        double y2 = l.getLongitude();

        double res = Math.sqrt((x1 - x2)*(y1 - y2) + (y1 - y2)*(y1 - y2));
        return res;
    }

    public double tempoViagem (Localizacao l){
        double tempo = 0;
        double speed = getRandomNumber(40,70);

        tempo = Math.floorDiv((int) distancia(l),(int) speed);

        return tempo; 

    }
    
}

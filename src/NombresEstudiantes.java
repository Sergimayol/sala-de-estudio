public class NombresEstudiantes {
    private static final String[] nombres = {
            "David", "Pablo", "Javier", "Alejandro", "Jordi", "Marc", "Marta", "Sandra", "Laura", "Mireia",
            "Maria", "Nuria", "Natalia", "Dani", "Jordi", "Albert", "Josep", "Joan", "Lluis", "Jordi", "Miquel", "Pere",
            "Santi", "Martí", "Enric", "Miguel", "Pol", "Sergi", "Eva", "Ángel", "Ángela", "Antonio", "Ariadna",
            "Armando", "Arturo", "Bárbara", "Beatriz", "Benito", "Benjamín", "Berta", "Blanca", "Carlos", "Carmen"};

    public static String getNombre(int i) {
        if (i < nombres.length) {
            return nombres[i];
        } else {
            throw new IllegalAccessError("Se ha superado el número de nombres disponibles");
        }
    }

}

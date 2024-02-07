import java.io.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class AddressBook {
    private Map<String, String> contacts;

    public AddressBook() {
        this.contacts = new HashMap<>();
    }

    public void load(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                contacts.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void list() {
        System.out.println("Contactos:");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public void create(String phoneNumber, String fullName) {
        contacts.put(phoneNumber, fullName);
        System.out.println("Contacto creado exitosamente.");
    }

    public void delete(String phoneNumber) {
        if (contacts.containsKey(phoneNumber)) {
            contacts.remove(phoneNumber);
            System.out.println("Contacto eliminado exitosamente.");
        } else {
            System.out.println("No se encontró el contacto con el número " + phoneNumber);
        }
    }

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook();
        addressBook.load("contacts.csv");

        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Eliminar contacto");
            System.out.println("4. Guardar y salir");
            System.out.print("Selecciona una opción: ");

            try {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            addressBook.list();
                            break;
                        case 2:
                            System.out.print("Ingrese el número telefónico: ");
                            String newPhoneNumber = scanner.next();
                            System.out.print("Ingrese el nombre completo: ");
                            scanner.nextLine(); // Consumir el salto de línea anterior
                            String newName = scanner.nextLine();
                            addressBook.create(newPhoneNumber, newName);
                            break;
                        case 3:
                            System.out.print("Ingrese el número telefónico a eliminar: ");
                            String phoneNumberToDelete = scanner.next();
                            addressBook.delete(phoneNumberToDelete);
                            break;
                        case 4:
                            addressBook.save("contacts.csv");
                            System.out.println("Cambios guardados. Saliendo...");
                            break;
                        default:
                            System.out.println("Opción no válida. Inténtelo de nuevo.");
                    }
                } else {
                    System.out.println("Entrada inválida. Introduce un número entero.");
                    scanner.nextLine(); // Limpiar el buffer
                    choice = 0; // Establecer un valor predeterminado para evitar un bucle infinito
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Introduce un número entero.");
                scanner.nextLine(); // Limpiar el buffer
                choice = 0; // Establecer un valor predeterminado para evitar un bucle infinito
            }
        } while (choice != 4);

        scanner.close();
    }
}
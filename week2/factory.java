/*
 * Exercise 2: Implementing the Factory Method Pattern (Java)
 * ============================================================
 *
 * The Factory Method pattern defines an interface for creating an object,
 * but lets subclasses decide which class to instantiate. It lets a class
 * defer instantiation to subclasses.
 *
 * Example domain: A notification system that can send different types
 * of notifications (Email, SMS, Push) without the client code needing
 * to know the concrete class being created.
 *
 * To compile and run:
 *   javac FactoryMethodPattern.java
 *   java FactoryMethodPattern
 */

// ---------------------------------------------------------------------
// Step 1: Product interface
// ---------------------------------------------------------------------
interface Notification {
    String send(String message);
}

// ---------------------------------------------------------------------
// Step 2: Concrete Products
// ---------------------------------------------------------------------
class EmailNotification implements Notification {
    @Override
    public String send(String message) {
        return "[Email] Sending message: '" + message + "'";
    }
}

class SMSNotification implements Notification {
    @Override
    public String send(String message) {
        return "[SMS] Sending message: '" + message + "'";
    }
}

class PushNotification implements Notification {
    @Override
    public String send(String message) {
        return "[Push] Sending message: '" + message + "'";
    }
}

// ---------------------------------------------------------------------
// Step 3: Creator (declares the factory method)
// ---------------------------------------------------------------------
abstract class NotificationCreator {

    // The Factory Method - subclasses override this to decide which
    // concrete product gets instantiated.
    protected abstract Notification createNotification();

    // Core business logic that relies on the product returned by the
    // factory method. This is the Creator's real responsibility -
    // it doesn't just create objects, it uses them.
    public String notify(String message) {
        Notification product = createNotification();
        return product.send(message);
    }
}

// ---------------------------------------------------------------------
// Step 4: Concrete Creators (override the factory method)
// ---------------------------------------------------------------------
class EmailNotificationCreator extends NotificationCreator {
    @Override
    protected Notification createNotification() {
        return new EmailNotification();
    }
}

class SMSNotificationCreator extends NotificationCreator {
    @Override
    protected Notification createNotification() {
        return new SMSNotification();
    }
}

class PushNotificationCreator extends NotificationCreator {
    @Override
    protected Notification createNotification() {
        return new PushNotification();
    }
}

// ---------------------------------------------------------------------
// Client code
// ---------------------------------------------------------------------
public class factory{
    

    private static void clientCode(NotificationCreator creator, String message) {
        // The client code works with the creator through its base
        // (abstract) type, so any concrete creator can be passed in.
        System.out.println(creator.notify(message));
    }

    public static void main(String[] args) {
        System.out.println("App: Launched with the Email notification creator.");
        clientCode(new EmailNotificationCreator(), "Your order has shipped!");

        System.out.println("\nApp: Launched with the SMS notification creator.");
        clientCode(new SMSNotificationCreator(), "Your OTP is 123456.");

        System.out.println("\nApp: Launched with the Push notification creator.");
        clientCode(new PushNotificationCreator(), "You have a new follower!");
    }
}
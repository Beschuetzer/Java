package major.adam;

public class Main {

    public static void main(String[] args) {
        PolitePerson tom = new PolitePerson("Tom");
        PolitePerson adam = new PolitePerson("Adam");

        new Thread(() -> {
            adam.sayHello(tom);
        }).start();
        tom.sayHello(adam);
    }

    static class PolitePerson {
        private final String name;

        public PolitePerson(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public synchronized void sayHello(PolitePerson person) {
            System.out.format("%s: %s" + " has said hello to me!%n", this.name, person.getName());

            //THIS LINE IS THE CAUSE OF THE DEADLOCK
            person.sayHelloBack(this);
        }

        public synchronized void sayHelloBack(PolitePerson person) {
            System.out.printf("%s: %s" + " has said hello back to me!%n", this.name, person.getName());
        }
    }
}

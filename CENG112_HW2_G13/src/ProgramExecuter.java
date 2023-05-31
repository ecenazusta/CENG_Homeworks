import java.util.Scanner;

public class ProgramExecuter {
	
	public static void main(String[] args) {
		run();
		}
	
    public static void run() {
    	
        try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("Enter available car count, N=");
			int numOfCars = Integer.parseInt(scanner.nextLine());
			System.out.print("Enter customer count, k=");
			int numOfCustomers = Integer.parseInt(scanner.nextLine());

			IDeque<Car> carWaitingList = new WaitingLine<Car>(numOfCars);
			for (int i = 0; i < numOfCars; i++) {
			    carWaitingList.addToBack(new Car());
			}

			IQueue<Customer> customerWaitingList = new WaitingLine<Customer>(numOfCustomers);
			for (int i = 0; i < numOfCustomers; i++) {
			    customerWaitingList.enqueue(new Customer());
			}

			Car car;
			Customer customer;
			CarList<Car> occupiedCars = new CarList<>();
			CarList<Customer> currentRenters = new CarList<>();
			int numOfDays = 0;
			boolean isRenting = true;
			int remainingCustomers = numOfCustomers;
			int remainingCars = numOfCars;

			while (isRenting) {
			    numOfDays++;
			    System.out.println("*******************" + "Day" + numOfDays + "*******************");
			    numOfCars = remainingCars;
			    for (int i = 1; i <= numOfCars; i++) {
			        car = carWaitingList.removeFront();
			        System.out.println("Current " + car + " quality=" + car.getQualityScore() + " is offering to");
			        numOfCustomers = remainingCustomers;
			        boolean carRented = false;
			        for (int j = 1; j <= numOfCustomers; j++) {
			            customer = customerWaitingList.dequeue();
			            if (car.getQualityScore() > customer.getQualityThreshold()) {
			                System.out.printf("\tCurrent " + customer + " threshold=" + "%.2f" + "\t\t\t" + "---accepted\n",
			                        customer.getQualityThreshold());
			                car.rent();
			                occupiedCars.add(car);
			                currentRenters.add(customer);
			                remainingCustomers--;
			                remainingCars--;
			                carRented = true;
			                break;
			            } else {
			                System.out.printf("\tCurrent " + customer + " threshold=" + "%.2f" + "\t\t\t" + "---not accepted\n",
			                        customer.getQualityThreshold());
			                customer.decreaseThreshold();
			                customerWaitingList.enqueue(customer);
			            }
			        }
			        if (!carRented) {
			            carWaitingList.addToBack(car);
			            System.out.println("\t---not accepted by any customer---");
			        }
			    }

			    System.out.println("All cars have been seen.");
			    isRenting = !customerWaitingList.isEmpty();

			    if (!isRenting) {
			        System.out.println("All customers rented a car.");
			    } else {
			        System.out.println("But there are still customers waiting.");
			        System.out.println("Rented cars: ");
			        for (int i = 1; i <= occupiedCars.getLength(); i++) {
			            Car occupiedCar = occupiedCars.getEntry(i);
			            Customer renter = currentRenters.getEntry(i);
			            System.out.println("\t" + occupiedCar + " by " + renter + " occupancy=" + occupiedCar.getLeftDays());
			            occupiedCar.decreaseOccupancy();
			        }

			        System.out.println("Available cars: ");
			        int tempLeftCars = remainingCars;
			        boolean availableCarsExist = false;

			        for (int i = 0; i < tempLeftCars; i++) {
			            Car availableCar = carWaitingList.removeFront();

			            if (availableCar.getLeftDays() != 1) {
			                System.out.println("\t" + availableCar);
			                availableCarsExist = true;
			            }
			            carWaitingList.addToBack(availableCar);
			        }
			        
			        for (int i = 1; i <= occupiedCars.getLength(); i++) {
			        Car occupiedCar = occupiedCars.getEntry(i);
			        if (occupiedCar.getLeftDays() == 0) {
			            occupiedCars.remove(i);
			            currentRenters.remove(i);
			            carWaitingList.addToFront(occupiedCar);
			            remainingCars++;
			            i--;
			        }
			        }
			        if (!availableCarsExist) {
			            System.out.println("No available cars.");
			        }
			        System.out.println("*******************" + "End of the Day" + "*******************:");
			    }
			}
		} 
        catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
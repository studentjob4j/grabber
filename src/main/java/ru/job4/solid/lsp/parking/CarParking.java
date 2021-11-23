package ru.job4.solid.lsp.parking;

/**
 * Парковка машин
 * @author Shegai Evgenii
 * @since 23.11.2021
 * @version 1.0
 */

public class CarParking implements Parking {

    private int truck;
    private int car;
    private final Auto[] simpleCar;
    private final Auto[] simpleTruck;
    private int truckIndex;
    private boolean check;

    public CarParking(int truck, int car) {
        this.truck = truck;
        this.car = car;
        this.simpleCar = new Auto[car];
        this.simpleTruck = new Auto[truck];
    }

    public int getCar() {
        return car;
    }

    public Auto[] getSimpleCar() {
        return simpleCar;
    }

    /**
     * метод добаляет машину на парковку если размер равен 1 и есть место на парковке для  легковых
     * машин то вызывается метод проверяющий есть ли свободное место для авто. Если есть
     * то под данным индексом записывается авто и счетчик свободных мест уменьшается на размер
     * авто .
     * Если это грузовик то его размер равен 2. Проверяется есть ли место на стоянке для грузовиков
     * если есть то грузовик добавляется и количество свободных мест уменьшается на
     * размер грузовика
     * Если на стоянке для грузовика нет мест , то проверяем есть ли места для грузовика на стоягке
     * для легковых авто. Если есть то проверяем находятся ли они рядом . т.к грузовику
     * нужно 2 места рядом. Если есть то добавляем грузовик на стоянку и уменьшаем
     * кол-во свободных мест на размер грузовика
     * @param auto
     * @return
     */

    @Override
    public boolean add(Auto auto) {
        boolean result = false;
        if (auto.getSize() == 1 && car != 0) {
            int carIndex = checkPlace(simpleCar, auto);
            this.simpleCar[carIndex] = auto;
            this.car--;
            result = true;
        } else {
            if (this.truck >= auto.getSize()) {
                this.simpleTruck[truckIndex++] = auto;
                this.truck = this.truck - auto.getSize();
                result = true;

            } else if (this.car >= auto.getSize()) {
                int index = checkPlace(simpleCar, auto);
                if (index != -1) {
                    result = addTruckOnCarParking(index, auto);
                    this.car = this.car - auto.getSize();
                    this.check = true;
                }
            }
        }
        return result;
    }

    private boolean addTruckOnCarParking(int index, Auto auto) {
        int size = auto.getSize();
        while (size != 0) {
            this.simpleCar[index++] = auto;
            size--;
        }
        return true;
    }

    /**
     * проверяет наличие свободных мест для легковой 1 место любое на стоянке или для
     * грузовика 2 места рядом на стоянке
     * @param array
     * @param auto
     * @return
     */

    private int checkPlace(Auto[] array, Auto auto) {
        boolean temp = true;
        int result = -1;
        int carSize = auto.getSize();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                int index = i;
                while (carSize != 0) {
                    if (array[index] != null) {
                        temp = false;
                    }
                    carSize--;
                    index++;
                }
                if (temp && carSize == 0) {
                    result = i;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * определяет тип авто и передает в метод где происходит удаление либо легковую либо
     * грузовую парковку и авто. И соответсвенно увеличивает счетчик свободных мест для
     * данной парковки
     * @param auto
     * @return
     */

    @Override
    public boolean remove(Auto auto) {
        boolean result = false;
        if (auto.getSize() == 1) {
            result = deleteFromParking(simpleCar, auto);
            if (result) {
                this.car++;
            }
        } else if (check) {
            result = deleteFromParking(simpleCar, auto);
            if (result) {
                this.car += auto.getSize();
            }
        } else  {
            result = deleteFromParking(simpleTruck, auto);
            if (result) {
                this.truck += auto.getSize();
            }
        }
        return result;
    }

    /**
     * удаляет авто из парковки легковой или из грузовой
     * @param array
     * @param auto
     * @return
     */

    private boolean deleteFromParking(Auto[] array, Auto auto) {
        boolean res = false;
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && array[i].equals(auto)) {
                if (this.truck < 2 || auto.getSize() == 1) {
                    this.simpleCar[i] = null;
                } else {
                    this.simpleTruck[i] = null;
                }
                count++;
            }
        }
        if (count == auto.getSize()) {
            res = true;
        }
        return res;
    }
}

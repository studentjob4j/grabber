package ru.job4.ref;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

/**
 * Метод get() всегда возвращает null, поэтому доступ можно осуществить только через ReferenceQueue
 * Из-за того что метод get() всегда возвращает null, становится непонятным а как все же понять
 * какой именно объект был удален. Для этого нужно создать собственный класс, который будет
 * наследовать PhantomReference, и который содержит некий дескриптор
 * PhantomReference попадает в ReferenceQueue только после выполнения finalize
 * (Предназначен этот метод
 * для автоматического освобождения системных ресурсов, занимаемых объектом,
 * на котором будет данный метод вызван.), и эту ссылку
 * есть смысл применять только вместе с очередью,
 * что значит мы еще имеем доступ к объекту некоторое время.
 * Контракт гарантирует что ссылка появится в очереди после того как GC заметит что объект доступен
 * только по phantom-ссылкам и перед тем как объект будет удален из памяти. Контракт не гарантирует,
 * что эти события произойдут одно за другим. В реальности между этими событиями
 * может пройти сколько
 * угодно времени. Поэтому не стоит опираться на PhantomReference для очистки
 * критически важных ресурсов.
 * Выполнение метода finalize() и добавление phantom-ссылки в ReferenceQueue
 * выполняется в разных запусках GC.
 * По этому если у объекта переопределен метод finalize() то для его удаления
 * необходимы 3 запуска GC,
 * а если метод не переопределен, то нужно, минимум, 2 запуска GC
 * * @author Shegai Evgenii
 *  * @since 28.10.2021
 *  * @version 1.0
 */

public class PhantomRefDemo {

    private static class MyPhantom extends PhantomReference<String> {

        private String name;

        public MyPhantom(String referent, ReferenceQueue<? super String> q, String name) {
            super(referent, q);
            this.name = name;
        }

        @Override
        public String get() {
            return name;
        }
    }

    private static class PhantomStorage {

        private ReferenceQueue<String> queue = new ReferenceQueue<>();
        private List<MyPhantom> phantoms = new LinkedList<>();

        public void add(String someData) {
            MyPhantom phantom = new MyPhantom(someData, queue, "my ref");
            phantoms.add(phantom);
        }

        public void utilizeResource() {
            for (ListIterator<MyPhantom> i = phantoms.listIterator(); i.hasNext();) {
                MyPhantom current = i.next();
                if (current != null && current.isEnqueued()) {
                    System.out.println("Utilized " + current.get());
                    current.clear();
                    i.remove();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PhantomStorage storage = new PhantomStorage();
        String data = "123".repeat(1000);
        storage.add(data);
        data = null;
        System.gc();
        TimeUnit.SECONDS.sleep(3);
        storage.utilizeResource();
    }
}

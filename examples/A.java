class A {
    int field1;
    int field2;

    void method1() {
        return;
    }

    class B {
        private void b() {
            method1();
            new C().method2();
        }

        public static void main(String... args) {
            b();
        }
    }
}

interface G {}

class D extends C implements G {
    int method2() {
        return 1;
    }
}

class C {
    int field3;

    int method2() {
        return field3;
    }
}

class A {
    int field1;
    int field2;

    void method1() {
        return;
    }

    class B {
        private void b() {
            for (int i = 0; i <= 100; i++) {
                int g = 0;
                for (int h = 0; h <= 100; h++) {
                    while (g < 67) {
                        g++;
                    }
                }
            }
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

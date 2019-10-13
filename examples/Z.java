class Z {
    public Z() {
        super();
        X x = new X();
        x.b().method2();
    }

    private C b() {
        method1();
        new C().method2();
        return new C();
    }
}
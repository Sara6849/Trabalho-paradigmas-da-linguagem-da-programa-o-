import threading

class ContadorInseguro:
    def __init__(self):
        self.valor = 0

    def incrementar(self):
        self.valor += 1  # não seguro

class ContadorSeguro:
    def __init__(self):
        self.valor = 0
        self.lock = threading.Lock()

    def incrementar(self):
        with self.lock:
            self.valor += 1  # seguro

def testar(contador, n_threads, n_incrementos):
    threads = []
    for _ in range(n_threads):
        t = threading.Thread(target=lambda: [
            contador.incrementar() for _ in range(n_incrementos)
        ])
        threads.append(t)
        t.start()

    for t in threads:
        t.join()

if __name__ == "__main__":
    n_threads = 10
    n_incrementos = 100_000

    print("\n--- Teste Contador Inseguro (gera race condition) ---")
    c1 = ContadorInseguro()
    testar(c1, n_threads, n_incrementos)
    print("Esperado =", n_threads * n_incrementos)
    print("Obtido   =", c1.valor)

    print("\n--- Teste Contador Seguro (com Lock) ---")
    c2 = ContadorSeguro()
    testar(c2, n_threads, n_incrementos)
    print("Esperado =", n_threads * n_incrementos)
    print("Obtido   =", c2.valor)

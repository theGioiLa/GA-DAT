# run a single seed with a specific input 
PROG_NAME = run-final-local-search-init-redundant-random-sbx4-local-search-mut-pc0.7-pm0.2
VERSION = 1.0
PROG = $(PROG_NAME)-$(VERSION).jar
SEED = 15 
SOURCE = lnoenu_400_4_10

BASE_PATH = ./cache
# SOURCE = l190_4_20
pc = 0.7
pm = 0.2

run:
	java -cp target/$(PROG) hust.mso.ga.Main $(SOURCE) $(pc) $(pm) $(BASE_PATH) $(SEED)

compile: 
	mvn package

INPUT = 450 4 20
avg:
	./scripts/run_avg $(INPUT)

# run average with a specific partner
AVG_SOURCE = 500 4 20 
single: 
	./scripts/run_parallel_seed $(AVG_SOURCE)

parse:
	./scripts/parse 4

# run average with the specific q
q = 4
avg_all:
	./scripts/run_avg_all $(q)

avg_quynq:
	./scripts/run-quynq $(q)

heur:
	java -cp target/$(PROG) hust.mso.ga.Run_Heur $(SOURCE) $(SEED) $(pc) $(pm)

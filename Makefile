# run a single seed with a specific input 
PROG_NAME = final-mixed-rand-init-redundant-random-local-search
VERSION = 1.0
PROG = $(PROG_NAME)-$(VERSION).jar
SEED = 1
# SOURCE = lnoend_100_4_20

PAPER = aTuan/test
SOURCE = $(PAPER)/l190_4_20
pc = 0.7
pm = 0.2

run:
	java -cp target/$(PROG) hust.mso.ga.Main $(SOURCE) $(SEED) $(pc) $(pm)

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

heur:
	java -cp target/$(PROG) hust.mso.ga.Run_Heur $(SOURCE) $(SEED) $(pc) $(pm)

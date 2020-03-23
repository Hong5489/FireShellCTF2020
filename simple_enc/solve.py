from z3 import *
solver = Solver()
flag_enc = open("flag.enc",'r').read()
local = [BitVec('b%i' % i, 8) for i in range(2)]
output = [ord(i) for i in flag_enc]
flag = [BitVec('f%i' % i, 8) for i in range(len(flag_enc))]

for i in range(len(flag_enc)):
	local[1] = flag[i]
	local[0] = (local[1] >> 7)
	local[1] = ~(local[1] * 2 | local[0]);
	solver.add(output[i] == local[1])
print solver.check()
modl = solver.model()

print ''.join([chr(modl[flag[i]].as_long()) for i in range(len(flag_enc))])
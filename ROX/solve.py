from pwn import *
flag = "a"*26
p = remote("142.93.113.55", 31087)
p.sendlineafter("start: ","start")
p.sendlineafter("Input: ",flag)
p.recvuntil("Output: ")
print xor(p.recvuntil("\n\n"),"a")

cadence = 8
tickrates = 128

i = 0
a = 0
b = []
while i < tickrates:
    i += 1
    if i % int(tickrates/cadence) is 0:
        a += 1
        b.append(i)
print(b, a)

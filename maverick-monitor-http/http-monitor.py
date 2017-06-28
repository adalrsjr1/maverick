import subprocess as sub
import re

p = sub.Popen(('sudo', 'tcpflow', '-l', '-p', '-c', '-i' , 'docker0', '-Ft'), stdout=sub.PIPE)
regex = "[0-9]{10}T(\S*)( \S*)*" # split this regex in groups
for row in iter(p.stdout.readline, b''):
  match = re.search(regex, row.rstrip())
  if match :
    print "%s" % (match.group(0))
  # send to rabbitmq

#! /usr/bin/env python3

import re
import sys

def main():
    f = open(sys.argv[1], encoding='utf8')
    content = f.read()
    #content = re.sub(r'\\\n', '', content, flags=re.UNICODE | re.MULTILINE)
    maxline = 0
    for line in content.split('\n'):
        if '%' not in line and len(line) > maxline:
            maxline = len(line)
    content2 = ''
    for line in content.split('\n'):
        if len(line) > 0:
            content2 += line + '%' * (maxline - len(line)) + '\n'
    sys.stdout.write(content2)

if __name__ == '__main__':
    main()

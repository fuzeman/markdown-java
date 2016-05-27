Traceback (most recent call last):
  File "C:\Python33\lib\site-packages\requests\packages\urllib3\connectionpool.py", line 421, in urlopen
    body=body, headers=headers)
  File "C:\Python33\lib\site-packages\requests\packages\urllib3\connectionpool.py", line 273, in _make_request
    conn.request(method, url, **httplib_request_kw)
  File "C:\Python33\lib\http\client.py", line 1049, in request
    self._send_request(method, url, body, headers)
  File "C:\Python33\lib\http\client.py", line 1087, in _send_request
    self.endheaders(body)
  File "C:\Python33\lib\http\client.py", line 1045, in endheaders
    self._send_output(message_body)
  File "C:\Python33\lib\http\client.py", line 890, in _send_output
    self.send(msg)
  File "C:\Python33\lib\http\client.py", line 828, in send
    self.connect()
  File "C:\Python33\lib\site-packages\requests\packages\urllib3\connectionpool.py", line 104, in connect
    ssl_version=resolved_ssl_version)
  File "C:\Python33\lib\site-packages\requests\packages\urllib3\util.py", line 329, in ssl_wrap_socket
    return context.wrap_socket(sock, server_hostname=server_hostname)
  File "C:\Python33\lib\ssl.py", line 210, in wrap_socket
    _context=self)
  File "C:\Python33\lib\ssl.py", line 310, in __init__
    raise x
  File "C:\Python33\lib\ssl.py", line 306, in __init__
    self.do_handshake()
  File "C:\Python33\lib\ssl.py", line 513, in do_handshake
    self._sslobj.do_handshake()
ConnectionResetError: [WinError 10054] An existing connection was forcibly closed by the remote host

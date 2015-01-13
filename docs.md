---
layout: page
title: Documentation
permalink: /docs/
---

<h2>Overview</h2>

<h3>Client</h3>

---
<h4>Client / SMPP Management</h4>
<p>Allows for SMPP client configurations to be constructed on the server, subsequently allowing for the server to bind to specified interfaces, and submit short messages.</p>

***Please refer to the ClientConfiguration model.***

<ol class="rest-ov">
	<li>
		<span class="rest-ov-m-post">POST</span>
		<span class="rest-ov-path">/smpp/client/configuration</span>
		<span class="rest-ov-brief">Constructs a new configuration.</span>
	</li>

	<li>
		<span class="rest-ov-m-get">GET</span>
		<span class="rest-ov-path">/smpp/client/configuration</span>
		<span class="rest-ov-brief">Lists all configurations.</span>
	</li>

	<li>
		<span class="rest-ov-m-get">GET</span>
		<span class="rest-ov-path">/smpp/client/configuration/:cid</span>
		<span class="rest-ov-brief">Shows details of configuration {cid}.</span>
	</li>

	<li>
		<span class="rest-ov-m-patch">PATCH</span>
		<span class="rest-ov-path">/smpp/client/configuration/:cid</span>
		<span class="rest-ov-brief">Update details of configuration {cid}.</span>
	</li>

	<li>
		<span class="rest-ov-m-delete">DELETE</span>
		<span class="rest-ov-path">/smpp/client/configuration/:cid</span>
		<span class="rest-ov-brief">Purge configuration {cid}.</span>
	</li>
</ol>

---
<h4>Client / Actions</h4>
<p>Allows for binds to be created, managed, destroyed and for short messages to be submitted, and log messages to be reviewed.</p>

<ol class="rest-ov">
	<li>
		<span class="rest-ov-m-get">GET</span>
		<span class="rest-ov-path">/smpp/client/configuration/:cid/bind</span>
		<span class="rest-ov-brief">Attempt to bind configuration {cid}.</span>
	</li>
	
	<li>
		<span class="rest-ov-m-post">POST</span>
		<span class="rest-ov-path">/smpp/client/configuration/:cid/bind/:bid/submit</span>
		<span class="rest-ov-brief">Submit message(s) to configuration {cid} child bind {bid}.</span>
	</li>
	
	<li>
		<span class="rest-ov-m-get">GET</span>
		<span class="rest-ov-path">/smpp/client/configuration/:cid/bind/:bid/destroy</span>
		<span class="rest-ov-brief">Cleanly destroy child bind {bid} from configuration {cid}.</span>
	</li>
	
	<li>
		<span class="rest-ov-m-get">GET</span>
		<span class="rest-ov-path">/smpp/client/configuration/:cid/bind/:bid/log</span>
		<span class="rest-ov-brief">Retrieve log statements from child bind {bid} of configuration {cid}.</span>
	</li>
	
	<li>
		<span class="rest-ov-m-delete">DELETE</span>
		<span class="rest-ov-path">/smpp/client/configuration/:cid/bind/:bid</span>
		<span class="rest-ov-brief">Purge bind (clean close is optional) {bid} of configuration {cid}.</span>
	</li>
</ol>

<h3>Server</h3>

---
<h4>Server / SMPP Management</h4>
<p>Allows for SMPP server sessions to be created on the server, subsequently allowing for the server to launch SMPP interfaces.</p>

***Please refer to the ServerConfiguration model.***

<ol class="rest-ov">
	<li>
		<span class="rest-ov-m-post">POST</span>
		<span class="rest-ov-path">/smpp/server/configuration</span>
		<span class="rest-ov-brief">Constructs a new configuration.</span>
	</li>
	
	<li>
		<span class="rest-ov-m-get">GET</span>
		<span class="rest-ov-path">/smpp/server/configuration</span>
		<span class="rest-ov-brief">Lists all configurations.</span>
	</li>
	
	<li>
		<span class="rest-ov-m-get">GET</span>
		<span class="rest-ov-path">/smpp/server/configuration/:cid</span>
		<span class="rest-ov-brief">Shows details of configuration {cid}.</span>
	</li>
	
	<li>
		<span class="rest-ov-m-patch">PATCH</span>
		<span class="rest-ov-path">/smpp/server/configuration/:cid</span>
		<span class="rest-ov-brief">Update details of configuration {cid}.</span>
	</li>
	
	<li>
		<span class="rest-ov-m-delete">DELETE</span>
		<span class="rest-ov-path">/smpp/server/configuration/:cid</span>
		<span class="rest-ov-brief">Purge configuration {cid}.</span>
	</li>
</ol>

---
<h4>Server / Actions</h4>
<p>Allows for SMPP interfaces to be started and stopped, and for review of log messages</p>

<ol class="rest-ov">
	<li>
		<span class="rest-ov-m-get">GET</span>
		<span class="rest-ov-path">/smpp/server/configuration/:cid/start</span>
		<span class="rest-ov-brief">Attempt to start service {cid}.</span>
	</li>

	<li>
		<span class="rest-ov-m-get">GET</span>
		<span class="rest-ov-path">/smpp/server/configuration/:cid/stop</span>
		<span class="rest-ov-brief">Attempt to stop service {cid}.</span>
	</li>
	
	<li>
		<span class="rest-ov-m-get">GET</span>
		<span class="rest-ov-path">/smpp/server/configuration/:cid/status</span>
		<span class="rest-ov-brief">Retrieve status of service {cid}.</span>
	</li>
	
	<li>
		<span class="rest-ov-m-get">GET</span>
		<span class="rest-ov-path">/smpp/server/configuration/:cid/log</span>
		<span class="rest-ov-brief">Retrieve log statements from service {cid}.</span>
	</li>
</ol>

<ul class="rest-ov">
	<li>
		<span class="rest-ov-m-post">POST</span> Methods always return an entity identifier.
	</li>
	
	<li>
		<span class="rest-ov-m-get">GET</span> Methods with an entity identifier will show, otherwise will list.
	</li>
	
	<li>
		<span class="rest-ov-m-get">PATCH</span> Methods map to update, multiple fields may be updated at once.
	</li>
	
	<li>
		<span class="rest-ov-m-delete">DELETE</span> Methods WILL purge data from the database. Be careful.
	</li>
</ul>

<h3>Design</h3>
---

<h4>Success HTTP Status Codes</h4>

* `200 OK` - Everything was fine.
* `201 OK` - An entity has been constructed, check the body for the identifier.
* `202 Accepted` - The server has accepted your request. It will be processed in due course.
* `204 No Content` - The server has accepted your request, but there was no content (empty body).

<h4>Client Error HTTP Status Codes</h4>

* `400 Bad Syntax` - Something was wrong in the request body.
* `401 Authentication` - You need to authenticate the request (i.e. include an authentication header).
* `402 Payment Required` - You need to top up your credits.
* `403 Refused Request` - You've been banned - check the request body to see why.
* `404 Not Found` - Request path is wrong / something was not found (entity identifier).
* `405 Bad Method` - Method wrong for path.
* `420 Enhance your Calm` - Rate limiting has been applied to your IP.

<h4>Server Error HTTP Status Codes</h4>

* `500 Error` - Something went wrong on the server. Consider raising a GitHub issue.
* `503 Unavailable` - Server is unavailable (due to too many requests)

<h4>Entity Identifiers</h4>
Entity identifiers are strings which allow for entities to be looked up in the database. Entity is a base class, which application specific entities subclass, therefore you must select the correct model (Java DTOs are available) to correspond to your request. Here's a list of the DTOs that we use in this project to simplify any client design you may embark upon. 

* ClientConfiguration
* ServerConfiguration

Additionally, here's JSON examples of the above Dtos: 

* ClientConfigurationJSON
* ServerConfigurationJSON

To ensure you're populating the DTOs with the correct information, check the `isValid` methods of each implementation.
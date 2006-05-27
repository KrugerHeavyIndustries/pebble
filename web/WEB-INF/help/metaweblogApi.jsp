<div class="contentItem">
  <div class="title">MetaWeblog API</div>
  <div class="subtitle">&nbsp;</div>

  <div class="contentItemBody">
    <p>
      Pebble has a comprehensive web-based administration interface but it also supports the MetaWeblog API for managing your blog content via compatible blogging clients. Examples here include MarsEdit, ecto, w.bloggar and others.
    </p>

    <div class="subsubtitle">Configuration</div>
    <p>
      When configuring a blogging client, you will typically be asked for the following information.
    </p>

    <ul>
      <li>Username : <authz:authentication operation="username"/></li>
      <li>Password : (the same you used to login here)</li>
      <li>XML-API : metaWeblog</li>
      <li>XML-RPC URL : ${pebbleContext.configuration.url}xmlrpc/</li>
      <li>Blog ID : ${blog.id}</li>
    </ul>

    <div class="subsubtitle">Uploading imagea and files</div>
    <p>
      With the MetaWeblog API you can upload images and other files to your blog.
      By default, any files you upload will be stored underneath <a href="./images/">Images</a>.
      To explicitly specify where the file should be uploaded to, prefix the filename with
      <code>images/</code> or <code>files/</code> to store the file under the <a href="./images/">Images</a> or <a href="./files/">Files</a> respectively. If required, you can also specify a directory structure in the filename such as <code>images/2004/someimage.jpg</code>.
    </p>
  </div>
</div>

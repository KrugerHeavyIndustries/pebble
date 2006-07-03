<div class="contentItem">
  <h1>Blog Entry Listeners</h1>
  <h2>&nbsp;</h2>

  <div class="contentItemBody">
    <p>
      Blog entry listeners are a type of Pebble plugin that allow custom code to be called
      whenever a blog entry is added, removed, changed, published or unpublished. The following blog entry listeners are included with the Pebble distribution and those
      marked with a (*) are configured by default.
    </p>

    <p>
      <b>net.sourceforge.pebble.event.blogentry.XmlRpcNotificationListener</b> (*)<br />
      Sends an <a href="./help/xmlrpcUpdatePings.html">XML-RPC update notification ping</a> when a blog entry is published.<br /><br />The list of websites can be specified in the <a href="configuration.jsp#xmlrpcNotifications">XML-RPC update notifications</a> section of the blog properties page.
    </p>

    <p>
      <b>net.sourceforge.pebble.event.blogentry.EmailNotificationListener</b><br />
      Sends a notification e-mail to the blog owner when a new blog entry is added, changed or published.
     </p>

    <p>
      <b>net.sourceforge.pebble.event.blogentry.MarkPendingListener</b><br />
      Marks all new and changed blog entries to be unpublished, so that they require approval from the blog owner before being published on the blog. Only the following subset of
      properties, when changed, will cause a blog entry to be marked as unpublished.
    </p>

    <ul>
      <li>Title</li>
      <li>Subtitle</li>
      <li>Excerpt</li>
      <li>Body</li>
      <li>Original permalink</li>
    </ul>

    <h3>Configuring blog entry listeners</h3>
    <p>
      To configure the blog entry listeners used by your blog, modify the list on the <a href="viewPlugins.secureaction#blogEntryListeners">Plugins</a> page. Your blog is using the
      following blog entry listeners.
    </p>
    <pre class="codeSample"><c:forEach var="listener" items="${blog.eventListenerList.blogEntryListeners}">${listener.class.name}<br /></c:forEach></pre>
  </div>
</div>
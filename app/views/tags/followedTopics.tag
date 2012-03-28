*{ widget that displays the list of topic activity. Designed to be in the left sidebar }*
<div class="list-fav favoriteTopics">
<h3>&{'followedTopics.title'}</h3>
#{list items:_followedTopicActivities , as:'followedTopicActivity'}
    <div class="item-fav">
        #{if followedTopicActivity.newInsightCount > 0}<span class="newInsightCount activityNotification">${followedTopicActivity.newInsightCount}</span>#{/if}
        <a href="@{Application.insights(null, null, null, followedTopicActivity.tag.label, null)}">${followedTopicActivity.tag.label}</a>
    </div>
#{/list}
    <hr class="clear"/>
</div>
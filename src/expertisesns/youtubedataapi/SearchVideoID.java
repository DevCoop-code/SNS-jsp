package expertisesns.youtubedataapi;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

import expertisesns.model.YouTubeChannelDto;

public class SearchVideoID 
{
	private static final String PROPERTIES_FILENAME = "youtube.properties";
	
	private static final long NUMBER_OF_VIDEOS_RETURNED = 25;
	
	private static YouTube youtube;
	
	public static List<YouTubeChannelDto> SearchVideoID(String query)
	{	
		List<YouTubeChannelDto> channels = null;
		
		Properties properties = new Properties();
        try 
        {
            InputStream in = SearchVideoID.class.getResourceAsStream(PROPERTIES_FILENAME);
            properties.load(in);
        }catch (IOException e) 
        {
            System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause()
                    + " : " + e.getMessage());
            System.exit(1);
        }
        
        try 
        {

	        youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() {
	            public void initialize(HttpRequest request) throws IOException {
	            }
	        }).setApplicationName("youtube-cmdline-search-sample").build();

            String queryTerm = query;

            YouTube.Search.List search = youtube.search().list("id,snippet");

            String apiKey = properties.getProperty("youtube.apikey");
            search.setKey(apiKey);
            search.setQ(queryTerm);

            search.setType("video");

            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

            // Call the API and print results.
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            
            if (searchResultList != null) 
            {
            	channels = prettyPrint(searchResultList.iterator(), queryTerm);
            }
        }catch (GoogleJsonResponseException e) 
        {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        }catch (IOException e) 
        {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        }catch (Throwable t) 
        {
            t.printStackTrace();
        }
        return channels;
	}
	private static List<YouTubeChannelDto> prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query) 
    {

		YouTubeChannelDto channel = null;
		List<YouTubeChannelDto> channels = new ArrayList<YouTubeChannelDto>();
		
        System.out.println("\n=============================================================");
        System.out.println(
                "   First " + NUMBER_OF_VIDEOS_RETURNED + " videos for search on \"" + query + "\".");
        System.out.println("=============================================================\n");

        if (!iteratorSearchResults.hasNext()) 
        {
            System.out.println(" There aren't any results for your query.");
        }

        while (iteratorSearchResults.hasNext()) 
        {

        	channel = new YouTubeChannelDto();
        	
            SearchResult singleVideo = iteratorSearchResults.next();
            ResourceId rId = singleVideo.getId();

            // Confirm that the result represents a video. Otherwise, the
            // item will not contain a video ID.
            if (rId.getKind().equals("youtube#video")) 
            {
                Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();

                System.out.println(" Video Id" + rId.getVideoId());
                System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
                System.out.println(" Thumbnail: " + thumbnail.getUrl());
                System.out.println("\n-------------------------------------------------------------\n");
                
                String video_title = singleVideo.getSnippet().getTitle().replaceAll("\"", "");
                channel.setId(rId.getVideoId());
                channel.setTitle(video_title);
                channel.setThumbnail(thumbnail.getUrl());
                
                channels.add(channel);
            }
        }
        
        return channels;
    }
}

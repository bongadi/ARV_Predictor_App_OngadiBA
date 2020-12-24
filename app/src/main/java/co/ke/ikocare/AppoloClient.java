package co.ke.ikocare;

import com.apollographql.apollo.ApolloClient;

public class AppoloClient {

    ApolloClient apolloClient = ApolloClient.builder()
            .serverUrl("https://hivdb.stanford.edu/page/graphiql/")
            .build();

}

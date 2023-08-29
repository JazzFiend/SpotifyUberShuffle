# SpotifyUberShuffle

## New Authentication

Getting an access token currently relies on running the tests in AuthenticationTest.
- First run authorizationCurl(). Copy the url into the browser and approve the popup.
- Then run accessTokenCurl(). Replace `code` with the reply from approving the popup. Replace `codeVerifier` with the first value from the previous step.
- Copy the resulting access token into the GUI like before.

## Next Steps

This process is obviously a mess. We should be doing this in code. The GUI is going to have to be revamped big time. But let's start with the backend first.

- Need to be able to handle the opening of the browser to approve the request. Or at least be able to send an approval via an API call. Start just by generating all the appropriate codes and sending that initial GET. See what that response looks like.
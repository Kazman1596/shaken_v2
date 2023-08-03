import org.example.dao.JdbcReviewDao;
import org.example.model.Review;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class JdbcReviewDaoTests extends BaseDaoTests{

    private Review testReview2 = new Review(20, 1, 15, "WOO!", "This was great", 4);
    private Review testReview;
    private JdbcReviewDao sut;

    @Before
    public void setup() throws SQLException {
        sut = new JdbcReviewDao(dataSource);
        testReview = new Review(1, 15, "Test Title", "Test Description", 5);
    }

    @Test
    public void getReviews_returns_correct_number_of_reviews() {
        List<Review> reviews = sut.getReviews();
        Assert.assertEquals(reviews.size(), 3);
    }

    @Test
    public void getReviewById_returns_correct_Review() {
        Review result = sut.getReviewById(20);

        assertReviewsMatch(testReview2, result);
    }
    @Test
    public void getReviewById_returns_null() {
        Review review = sut.getReviewById(99);

        Assert.assertNull(review);
    }

    @Test
    public void getReviewsByRecipe_returns_correct_reviews() {
        List<Review> results = sut.getReviewsByRecipe(1);
        Assert.assertEquals(results.size(), 2);
    }

    @Test
    public void getReviewsByRecipe_returns_0_if_recipe_has_none_or_doesnt_exist() {
        List<Review> vodkaMuleResults = sut.getReviewsByRecipe(3);
        List<Review> nullRecipeResults = sut.getReviewsByRecipe(180);
        Assert.assertEquals(vodkaMuleResults.size(), 0);
        Assert.assertEquals(nullRecipeResults.size(), 0);
    }

    @Test
    public void getReviewsByUser_returns_correct_reviews() {
        List<Review> results = sut.getReviewsByUser(14);
        Assert.assertEquals(results.size(), 2);
    }

    @Test
    public void getReviewsByUser_returns_0_if_user_has_none_or_doesnt_exist() {
        List<Review> defaultUserResults = sut.getReviewsByUser(1);
        List<Review> nullUserResults = sut.getReviewsByRecipe(900);
        Assert.assertEquals(defaultUserResults.size(), 0);
        Assert.assertEquals(nullUserResults.size(), 0);
    }

    @Test
    public void getReviewsByRating_returns_correct_reviews() {
        List<Review> results = sut.getReviewsByRating(5);
        Assert.assertEquals(results.size(), 1);
    }

    @Test
    public void getReviewsByRating_returns_0_if_none_or_doesnt_exist() {
        List<Review> lowResult = sut.getReviewsByRating(1);
        List<Review> nullResult = sut.getReviewsByRating(10);
        Assert.assertEquals(lowResult.size(), 0);
        Assert.assertEquals(nullResult.size(), 0);
    }

    @Test
    public void createReview_returns_new_review() {
        Review newReview = sut.createReview(testReview);

        int newId = newReview.getReviewId();
        Assert.assertTrue(newId > 0);

        Review retrievedReview = sut.getReviewById(newId);
        assertReviewsMatch(newReview, retrievedReview);
    }

    @Test
    public void updateReview_has_expected_values() {
        Review reviewToUpdate = sut.getReviewById(20);
        reviewToUpdate.setDescription("I hated it");
        reviewToUpdate.setTitle("Disgusting");

        sut.updateReview(reviewToUpdate);

        Review retrievedReview = sut.getReviewById(20);
        assertReviewsMatch(reviewToUpdate, retrievedReview);
    }
    @Test
    public void deleted_review_cant_be_retrieved() {
        sut.deleteReview(20);

        Review retrievedReview = sut.getReviewById(20);

        Assert.assertNull(retrievedReview);
    }

    private void assertReviewsMatch(Review expected, Review result) {
        Assert.assertEquals(expected.getReviewId(), result.getReviewId());
        Assert.assertEquals(expected.getRecipeId(), result.getRecipeId());
        Assert.assertEquals(expected.getUserId(), result.getUserId());
        Assert.assertEquals(expected.getTitle(), result.getTitle());
        Assert.assertEquals(expected.getDescription(), result.getDescription());
    }

}

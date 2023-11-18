import java.util.LinkedList;

class Book {

    private final String isbn;
    private final String title;
    private final String author;
    private final String pubDate;
    private final String publisher;
    private final Genre genre;
    private LinkedList<Review> reviews;
    private float rating;
    private int sumRating;

    public Book(String isbn, String title, String author, String pubDate, String publisher, Genre genre) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.pubDate = pubDate;
        this.publisher = publisher;
        this.genre = genre;
        reviews = new LinkedList<>();
        rating = 0;
        sumRating = 0;
    }

    /**
     * adds a review to the list of reviews
     * updates the average rating by adjusting it with the new rating
     *
     * @param review
     */
    public void addReview(Review review) {
        reviews.add(review);
        calculateRating();
//        System.out.println("CALRATING: " + this.getReviewRating());
//        this.rating = (((reviews.size()-1) * this.getReviewRating()) + review.getRating())/ (reviews.size());
//        this.rating = ((this.rating * (this.reviews.size() - 2)) + rating) / this.reviews.size()-1;
//        this.rating = ((this.rating * (reviews.size() - 1)) + review.getRating()) / reviews.size();
//        System.out.println("NEWRATING: " + this.getReviewRating());
    }

    /**
     * @ Qamar
     * Calculates an average rating for the book
     * Called after the reviews arraylist is populated
     * Used for popularity heaptrees
     */
    void calculateRating() {
        float sum = 0;
        for (Review review : reviews) {
            sum += review.getRating();
        }
        this.rating = sum / (float) reviews.size();
    }

    public void addReview(String review, int rating) {
        addReview(new Review(review, rating));
    }

    public String getISBN() {
        return isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre.toString();
    }

    public LinkedList<Review> getReviews() {
        return reviews;
    }


    public float getReviewRating() {
        return rating;
    }

    public String getPubDate() {
        return pubDate;
    }

    @Override
    public String toString() {
        return ">> " + isbn + " > " + title + " > " + author + " > " + pubDate + " > " + publisher + " > " + getGenre() + reviews + " > " + getReviewRating() + "<<";
    }
}

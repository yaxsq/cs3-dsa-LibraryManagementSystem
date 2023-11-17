class Review {

    private String review;
    private int rating;

    public Review(String review, int rating) {
        this.review = review;
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return ">> " + review + " > " + rating + " <<";
    }
}

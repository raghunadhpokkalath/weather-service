describe('Unauthorized error for Invalid API Key', function () {
        it('Return 401 for invalid country', function (done) {
                request.get('data/description?county=Australia&city=Melbourne')
                        .set('Content-Type', 'application/json')
                        .set('x-api-key', 'd49ebe764fe53c5ca867f87c')
                        .send()
                        .end((err, res) => {
                                if (err) return done(err);
                                expect(res.status).to.eql(401);
                                expect(res.body.message).to.eql('Invalid Api Key');
                                done();
                        });
        });
});
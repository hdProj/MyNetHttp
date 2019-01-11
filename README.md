# 简单网络请求封装
LocalService.getApi().getHealthClassify()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<List<HealthClassifyBean>>() {
                    @Override
                    protected void onDone(List<HealthClassifyBean> list) {

                       
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                                           
					}
                });





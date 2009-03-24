if (!global.fpmedv) {
    global.fpmedv = {};
}

fpmedv.JdbcStatistics = {};

fpmedv.JdbcStatistics.renderStatistics = function () {
   var stats = Packages.at.fpmedv.jdbc.Logger.getInstance();
   var param = {};
   param.nocatTotalCount = stats.getCount(stats.DEFAULT);
   param.nocatTotalMillis = stats.getMillis(stats.DEFAULT);
   param.nocatTotalAverage = param.nocatTotalMillis / param.nocatTotalCount;
   param.selectsTotalCount = stats.getCount(stats.SELECT);
   param.selectsTotalMillis = stats.getMillis(stats.SELECT);
   param.selectsTotalAverage = param.selectsTotalMillis / param.selectsTotalCount;
   param.updatesTotalCount = stats.getCount(stats.UPDATE);
   param.updatesTotalMillis = stats.getMillis(stats.UPDATE);
   param.updatesTotalAverage = param.updatesTotalMillis / param.updatesTotalCount;
   param.insertsTotalCount = stats.getCount(stats.INSERT);
   param.insertsTotalMillis = stats.getMillis(stats.INSERT);
   param.insertsTotalAverage = param.insertsTotalMillis / param.insertsTotalCount;
   param.deletesTotalCount = stats.getCount(stats.DELETE);
   param.deletesTotalMillis = stats.getMillis(stats.DELETE);
   param.deletesTotalAverage = param.deletesTotalMillis / param.deletesTotalCount;
   param.loggingEnabled = stats.isLoggingEnabled();
   param.errorCount = stats.getErrorCount();

   statements = stats.getNormalizedStatements();
   statementKeys = statements.keySet();
   statementKeysIterator = statementKeys.iterator();
   param.statementsSize = statements.size();
   param.maxStatements = stats.getMaxStatements();
   param.statementStatisticsOverflow = stats.getStatementStatisticsOverflow();
   
   renderSkin("fpmedv.JdbcStatistics.StatmentsHeader", param);
   while (statementKeysIterator.hasNext()) {
      param.key = statementKeysIterator.next();
      var stmt = statements.get(param.key);
      param.count = stmt.getCount();
      param.millis = stmt.getMillis();
      param.average = param.millis / param.count;
      renderSkin("fpmedv.JdbcStatistics.StatmentsRow", param);
   }
   renderSkin("fpmedv.JdbcStatistics.StatmentsFooter", param);
};

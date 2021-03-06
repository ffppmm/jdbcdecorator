if (!global.fpmedv) {
    global.fpmedv = {};
}

fpmedv.JdbcStatistics = {};

fpmedv.JdbcStatistics.renderStatistics = function () {
   var stats = Packages.at.fpmedv.jdbc.Statistics.getInstance();
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

   param.totalCount = param.nocatTotalCount + param.selectsTotalCount + param.updatesTotalCount + param.insertsTotalCount + param.deletesTotalCount;
   param.totalMillis = param.nocatTotalCount + param.selectsTotalMillis + param.updatesTotalMillis + param.insertsTotalMillis + param.deletesTotalMillis;

   param.statisticsEnabled = stats.areStatisticsEnabled() ? "enabled" : "disabled";
   param.errorCount = stats.getErrorCount();

   statements = stats.getNormalizedStatements();
   statementKeys = statements.keySet();
   statementKeysIterator = statementKeys.iterator();
   param.statementsSize = statements.size();
   param.maxStatements = stats.getMaxStatements();
   param.statementStatisticsOverflow = stats.getStatementStatisticsOverflow();
   
   param.statisticsStarted = stats.getStatisticsStarted();
   // get millis
   param.now = new Date();
   param.statisticsStartedMillis = param.now - param.statisticsStarted.getTime();
   param.statisticsStartedSeconds = param.statisticsStartedMillis / 1000;
   param.statementsPerSecond = param.totalCount / param.statisticsStartedSeconds;
   
   // convert to string
   param.statisticsStarted = param.statisticsStarted.toString();
   
   renderSkin("fpmedv.JdbcStatistics.StatmentsHeader", param);
   var i = 0;
   while (statementKeysIterator.hasNext()) {
      param.key = statementKeysIterator.next();
      param.position = i++; 
      var stmt = statements.get(param.key);
      param.count = stmt.getCount();
      param.millis = stmt.getMillis();
      param.average = parseInt((param.millis / param.count), 10);
      param.percentageMillis = parseInt(param.millis / (param.totalMillis / 100), 10);
      param.percentageCount = parseInt(param.count / (param.totalCount / 100), 10);
      param.statementType = param.key.substring(0, 1).toUpperCase();
      param.lastCommit = stmt.getLastCommit();
      param.lastCommitTimestamp = param.lastCommit.getTime();
      renderSkin("fpmedv.JdbcStatistics.StatmentsRow", param);
   }
   renderSkin("fpmedv.JdbcStatistics.StatmentsFooter", param);
};

fpmedv.JdbcStatistics.resetStatistics = function () {
	var logger = Packages.at.fpmedv.jdbc.Statistics.getInstance();
	logger.resetStatistics();
};

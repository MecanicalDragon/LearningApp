# Module curator-app 

CuratorApp is intended to instantiate several features with Zookeeper when you use Spring:
- `LeaderWorker` shows leader election recipe with CuratorFramework.
- `SimpleController` instantiates work with zookeeper distributed locks, counters and zNodes.
- `ZookeeperListener` depicts persistent and disposable watchers.
- `RemoteService` outputs zookeeper-fetched configuration. All config for it is in properties file.
- `LeaderCandidate` instantiates leader-election with spring-integration library.